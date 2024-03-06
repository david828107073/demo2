package com.david.controller;

import com.david.common.BasicOut;
import com.david.vo.SystemUserVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @PostMapping(value = "/auth/login")
    public BasicOut<Void> login(SystemUserVO systemUserVO){
        return null;
    }
}
