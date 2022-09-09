package com.thf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@SpringBootApplication
@MapperScan("com.thf.dao")
public class SpringthfApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringthfApplication.class, args);
    }
}
