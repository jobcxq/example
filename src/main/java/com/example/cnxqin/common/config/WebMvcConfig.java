package com.example.cnxqin.common.config;

import com.example.cnxqin.common.filter.RequestFilter;
import com.example.cnxqin.common.interceptor.UserAccessInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author cnxqin
 * @desc
 * @date 2021/01/22 22:36
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public RequestFilter requestFilter(){
        return new RequestFilter();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(new UserAccessInterceptor());
        //所有路径都被拦截
        registration.addPathPatterns("/**");
        registration.excludePathPatterns("/public/*", "/login/*", "/error");
    }
}
