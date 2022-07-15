package com.thf.config;

import com.thf.common.interceptors.CheckTokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private CheckTokenInterceptor checkTokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(checkTokenInterceptor)
//                .addPathPatterns("/shopcart/**")
                .addPathPatterns("/orders/**")
                .addPathPatterns("/users/reset/**")
                .addPathPatterns("/users/update")
                .addPathPatterns("/users/info")
                .addPathPatterns("/project/**");
               // .excludePathPatterns("/users/**");
    }
}

