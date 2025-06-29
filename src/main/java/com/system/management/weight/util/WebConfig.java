package com.system.management.weight.util;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer {
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")            
                .excludePathPatterns("/login","/logout", "/css/**", "/js/**","/ToMyPage","/myPage","/register","/confirm","/complete");  
    }
}
