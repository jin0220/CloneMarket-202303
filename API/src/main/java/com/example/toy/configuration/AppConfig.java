package com.example.toy.configuration;

import com.example.toy.interceptor.JwtTokenInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class AppConfig implements WebMvcConfigurer {
    private final JwtTokenInterceptor jwtTokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtTokenInterceptor)
                .excludePathPatterns("/api/v1/signin", "/api/v1/login", "/api/v1/refresh", "/api/v1/location", "/api/v1/sms", "/api/v1/profile"); // 로그인, 회원가입, 토큰 재발급은 인터셉트에서 제외
//                .excludePathPatterns("/api/v1/*");
    }
}
