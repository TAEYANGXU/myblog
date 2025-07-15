package com.xyz.myblog.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xyz.myblog.dto.response.ApiResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class GlobalResponseAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        // 所有响应都处理
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {

        // 已经是统一格式，直接返回
        if (body instanceof ApiResponse) {
            return body;
        }

        // 如果body是Map并且包含code且code!=200，直接返回（错误响应）
        if (body instanceof java.util.Map) {
            Object code = ((java.util.Map<?, ?>) body).get("code");
            if (code instanceof Integer && ((Integer) code) != 200) {
                return body;
            }
        }

        // 针对字符串特殊处理（防止 ResponseBodyAdvice 和 StringHttpMessageConverter 冲突）
        if (body instanceof String) {
            // 注意：此时不能直接返回 ApiResponse，会报错，需转换成 JSON 字符串
            try {
                return new ObjectMapper().writeValueAsString(ApiResponse.success(body));
            } catch (Exception e) {
                return ApiResponse.error(500, "JSON 序列化失败");
            }
        }

        return ApiResponse.success(body);
    }
}
