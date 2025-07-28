package com.xyz.myblog.exception;

import com.xyz.myblog.controller.AuthController;
import com.xyz.myblog.dto.response.ApiResponse;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handleBadCredentials(BadCredentialsException ex) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 403);
        result.put("message", "用户名或密码错误");
        return new ResponseEntity<>(result, HttpStatus.FORBIDDEN);
    }

    // 可根据需要添加其他异常处理
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception ex) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 500);
        result.put("message", "服务器错误");
        result.put("error", ex.getMessage());  // 添加具体错误信息
        result.put("exception", ex.getClass().getSimpleName());  // 添加异常类型
        // 在开发环境打印完整堆栈信息
//        ex.printStackTrace();
        return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<Map<String, Object>> handleJwtException(JwtException ex) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 401);
        result.put("message", "无效的token");
        return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
    }

    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    public ApiResponse<Map<String, Object>> handleRuntimeException(RuntimeException e) {
        logger.error("Runtime exception: ", e); // 添加日志
        return ApiResponse.error(400, e.getMessage());
    }
} 