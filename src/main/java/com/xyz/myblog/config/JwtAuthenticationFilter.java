package com.xyz.myblog.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xyz.myblog.service.impl.UserDetailsServiceImpl;
import com.xyz.myblog.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        // 1. 检查Authorization头是否存在
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);

        try {
            // 2. 提取用户名
            username = jwtUtil.extractUsername(jwt);

            // 3. 验证上下文和Token有效性
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (jwtUtil.validateToken(jwt)) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } else {
                    sendErrorResponse(response,
                            HttpStatus.UNAUTHORIZED.value(),
                            "Token验证失败");
                    return;
                }
            }

        } catch (ExpiredJwtException ex) {
            logger.warn("Token已过期: " + ex.getMessage());
            sendErrorResponse(response,
                    HttpStatus.UNAUTHORIZED.value(),
                    "Token已过期，请重新登录");
            return;

        } catch (SignatureException | MalformedJwtException ex) {
            logger.warn("无效的Token签名: " + ex.getMessage());
            sendErrorResponse(response,
                    HttpStatus.UNAUTHORIZED.value(),
                    "无效的Token签名");
            return;

        } catch (UsernameNotFoundException ex) {
            logger.warn("用户不存在: " + ex.getMessage());
            sendErrorResponse(response,
                    HttpStatus.NOT_FOUND.value(),
                    "用户不存在");
            return;

        } catch (Exception ex) {
            logger.error("认证过程中发生错误: ", ex);
            sendErrorResponse(response,
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "服务器内部错误");
            return;
        }

        filterChain.doFilter(request, response);
    }

    /**
     * 发送统一的错误响应
     */
    private void sendErrorResponse(HttpServletResponse response,
                                   int status,
                                   String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json;charset=UTF-8"); // 关键点：强制UTF-8编码
        response.setCharacterEncoding("UTF-8"); // 确保响应体编码

        Map<String, Object> body = new HashMap<>();
        body.put("status", status);
        body.put("message", message);
//        body.put("path", response.get);

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.getWriter().flush();
    }
}