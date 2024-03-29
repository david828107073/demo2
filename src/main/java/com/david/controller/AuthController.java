package com.david.controller;

import com.david.common.BasicOut;
import com.david.common.CustomerException;
import com.david.common.HttpStatusEnum;
import com.david.dto.SystemUserDTO;
import com.david.service.AuthService;
import com.david.util.JwtUtil;
import com.david.vo.LoginUserVO;
import com.david.vo.SystemUserVO;
import com.david.vo.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "帳號管理 API", description = "帳號新增、登入驗證")
public class AuthController {
    final AuthService authService;
    final JwtUtil jwtUtil;

    @PostMapping(value = "/auth/add")
    @Operation(summary = "新增帳號")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SystemUserDTO.class)
                    )),
            @ApiResponse(responseCode = "500", description = "系統錯誤", content = @Content()),
            @ApiResponse(responseCode = "E003", description = "帳號已存在", content = @Content())
    })
    public BasicOut<SystemUserDTO> createUser(HttpServletRequest request, @RequestBody SystemUserVO systemUserVO) {
        BasicOut<SystemUserDTO> result = new BasicOut<>();
        try {
            checkInputData(systemUserVO, request);
            SystemUserDTO systemUserDTO = authService.createUser(systemUserVO);
            result.setRetCode(HttpStatusEnum.SUCCESS.getCode());
            result.setMessage(HttpStatusEnum.SUCCESS.getMessage());
            result.setBody(systemUserDTO);
        } catch (Exception e) {
            getErrorResponse(e, result);
        }
        return result;
    }

    @PostMapping(value = "/auth/login")
    @Operation(summary = "登入", description = "登入後會回傳 Token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SystemUserDTO.class)
                    )),
            @ApiResponse(responseCode = "401", description = "帳號密碼有誤", content = @Content()),
            @ApiResponse(responseCode = "500", description = "系統錯誤", content = @Content())
    })
    public BasicOut<SystemUserDTO> login(HttpServletRequest request, HttpServletResponse response, @RequestBody LoginUserVO loginUserVO) {
        BasicOut<SystemUserDTO> result = new BasicOut<>();
        try {
            checkInputData(loginUserVO, request);
            SystemUserDTO systemUserDTO = authService.login(loginUserVO);
            String username = systemUserDTO.getUsername();
            String account = systemUserDTO.getAccount();
            Map<String, Object> payload = Map.of("username", username, "account", account);
            String token = jwtUtil.generateToken(payload);
            result.setRetCode(HttpStatusEnum.SUCCESS.getCode());
            result.setMessage(HttpStatusEnum.SUCCESS.getMessage());
            result.setBody(systemUserDTO);
            response.setHeader("Authorization", "Bearer " + token);
        } catch (Exception e) {
            getErrorResponse(e, result);
        }
        return result;
    }

    private void checkInputData(User userVO, HttpServletRequest request) throws CustomerException {
        String apiPath = request.getRequestURI();
        log.info("request path:{}", apiPath);
        List<String> errors = new ArrayList<>();
        if (!StringUtils.hasText(userVO.getAccount())) {
            errors.add("使用者名稱");
        }
        if (!StringUtils.hasText(userVO.getPassword())) {
            errors.add("密碼");
        }
        if (userVO instanceof SystemUserVO systemUserVO && !StringUtils.hasText(systemUserVO.getName())) {
            errors.add("名稱");
        }
        StringJoiner stringJoiner = new StringJoiner("、", "請輸入", "");
        if (!CollectionUtils.isEmpty(errors)) {
            errors.forEach(stringJoiner::add);
            throw new CustomerException(HttpStatusEnum.INPUT_VALUE_VALIDATE_ERROR, stringJoiner.toString());
        }
    }

    private void getErrorResponse(Exception e, BasicOut<?> result) {
        String errorMessage;
        String errorCode;
        if (e instanceof CustomerException c) {
            errorCode = c.getErrorCode();
            errorMessage = c.getErrorMessage();
        } else {
            errorMessage = HttpStatusEnum.SYSTEM_ERROR.getMessage();
            errorCode = HttpStatusEnum.SYSTEM_ERROR.getCode();
        }
        result.setMessage(errorMessage);
        result.setRetCode(errorCode);
    }

}
