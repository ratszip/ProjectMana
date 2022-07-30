package com.thf.config;

import com.thf.common.interceptors.CheckTokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private CheckTokenInterceptor checkTokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(checkTokenInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/users/verifycode", "/users/login", "/users/register")
                .excludePathPatterns("/swagger-resources/**", "/v2/**", "/swagger-ui.html/**", "/swagger-resources/**","/webjars/**","/doc.html/**")
                .excludePathPatterns("/open/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //将templates目录下的CSS、JS文件映射为静态资源，防止Spring把这些资源识别成thymeleaf模版
        registry.addResourceHandler("/templates/**.js").addResourceLocations("classpath:/templates/");
        registry.addResourceHandler("/templates/**.css").addResourceLocations("classpath:/templates/");
        //其他静态资源
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        //swagger增加url映射
        registry.addResourceHandler("swagger-ui.html","doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}


