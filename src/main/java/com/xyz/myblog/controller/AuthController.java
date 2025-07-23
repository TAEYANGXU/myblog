package com.xyz.myblog.controller;


import com.xyz.myblog.dto.request.LoginDTO;
import com.xyz.myblog.dto.response.ApiResponse;
import com.xyz.myblog.service.LoginUser;
import com.xyz.myblog.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ApiResponse<Map<String, Object>> login(@RequestBody LoginDTO request) {
        System.out.println(new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode(request.getPassword()));
        logger.info("收到登录请求: 用户名={}, 密码={}", request.getUsername(), request.getPassword());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(), request.getPassword()));
        logger.info("认证成功: {}", authentication.isAuthenticated());
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String token = jwtUtil.generateToken(loginUser.getUsername());
        logger.info("生成token: {}", token);
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("user", loginUser.getUser());
        logger.info("返回结果: {}", result);
        return ApiResponse.success(result);
    }
}
