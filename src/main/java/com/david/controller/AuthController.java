package com.david.controller;

import com.david.common.BasicOut;
import com.david.common.CustomerException;
import com.david.common.HttpStatusEnum;
import com.david.dto.SystemUserDTO;
import com.david.service.AuthService;
import com.david.util.JwtUtil;
import com.david.vo.SystemUserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    final AuthService authService;
    final JwtUtil jwtUtil;

    @PostMapping(value = "/auth/add")
    public BasicOut<String> createUser(@RequestBody SystemUserVO systemUserVO) {
        BasicOut<String> result = new BasicOut<>();
        try {
            authService.createUser(systemUserVO);
            result.setRetCode(HttpStatusEnum.SUCCESS.getErrorCode());
            result.setMessage(HttpStatusEnum.SUCCESS.getMessage());
            result.setBody("新增成功");
            checkInputData(systemUserVO);
        } catch (CustomerException e) {
            result.setMessage(e.getMessage());
            result.setRetCode(HttpStatusEnum.INPUT_VALUE_VALIDATE_ERROR.getErrorCode());
        } catch (Exception e) {
            log.error("add failed reason: {}", e.getLocalizedMessage());
            result.setMessage(HttpStatusEnum.UNKNOWN_ERROR.getMessage());
            result.setBody(e.getLocalizedMessage());
            result.setRetCode(HttpStatusEnum.UNKNOWN_ERROR.getErrorCode());
        }
        return result;
    }

    private void checkInputData(SystemUserVO userVO) throws CustomerException {
        List<String> errors = new ArrayList<>();
        if (!StringUtils.hasText(userVO.getName())) {
            errors.add("使用者名稱");
        }
        if (!StringUtils.hasText(userVO.getAccount())) {
            errors.add("帳號");
        }
        if (!StringUtils.hasText(userVO.getName())) {
            errors.add("名稱");
        }
        StringJoiner stringJoiner = new StringJoiner("請輸入");
        if (!CollectionUtils.isEmpty(errors)) {
            errors.forEach(error -> stringJoiner.add(error));
            throw new CustomerException(stringJoiner.toString());
        }
    }

    @PostMapping(value = "/auth/login")
    public BasicOut<String> login(@RequestBody SystemUserVO systemUserVO) {
        //TODO check 帳密
        BasicOut<String> result = new BasicOut<>();
        try {
            SystemUserDTO systemUserDTO = authService.login(systemUserVO);
            String username = systemUserDTO.getUsername();
            String account = systemUserDTO.getAccount();
            Map<String, Object> payload = Map.of("username", username, "account", account);
            String token = jwtUtil.generateToken(payload);
            result.setRetCode(HttpStatusEnum.SUCCESS.getErrorCode());
            result.setMessage(HttpStatusEnum.SUCCESS.getMessage());
            result.setBody(token);
        } catch (CustomerException e) {
            //TODO
//            result.setRetCode(e);
            e.printStackTrace();
        } catch (Exception e) {
            //TODO
            e.printStackTrace();
        }
        return result;
    }
}
