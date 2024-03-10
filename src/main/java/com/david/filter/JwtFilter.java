package com.david.filter;

import com.david.common.BasicOut;
import com.david.common.HttpStatusEnum;
import com.david.util.GsonUtil;
import com.david.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    final JwtUtil jwtUtil;
    final GsonUtil gsonUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String authorization = request.getHeader("Authorization");
            if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer ")) {
                String token = authorization.replace("Bearer ", "");
                Map<String, Object> payload = jwtUtil.parse(token);
                Object account = payload.get("account");
                Authentication authentication = new UsernamePasswordAuthenticationToken(account, payload, List.of());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            log.error("jwtFilter error occurred, reason: {}", e.getLocalizedMessage());
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            BasicOut<Void> result = new BasicOut<>();
            result.setMessage(HttpStatusEnum.PERMISSION_DENIED.getMessage());
            result.setRetCode(HttpStatusEnum.PERMISSION_DENIED.getCode());
            response.setCharacterEncoding("UTF-8");
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(gsonUtil.toJSON(result));
            return;
        }
        filterChain.doFilter(request, response);
    }
}
