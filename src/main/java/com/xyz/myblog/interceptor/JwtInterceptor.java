package com.xyz.myblog.interceptor;

import com.xyz.myblog.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            writeError(response, "缺少或格式错误的 Token");
            return false;
        }

        token = token.replace("Bearer ", "");
        if (!jwtUtil.validateToken(token)) {
            writeError(response, "无效或过期的 Token");
            return false;
        }

        return true;
    }

    private void writeError(HttpServletResponse response, String msg) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(401);
        response.getWriter().write("{\"code\":401,\"message\":\"" + msg + "\",\"data\":null,\"timestamp\":" + System.currentTimeMillis() + "}");
    }
}
