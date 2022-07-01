package com.thf.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class SwaggerConfig {

    /*
     swaggers会生成api接口文档
     1.配置生成的文档信息
     2.配置生成规则
     */

    /**
     * 封装接口信息
     * @return
     */
    @Bean
    public Docket getDocket(){
        Docket docket=new Docket(DocumentationType.SWAGGER_2);//文档风格
        ApiInfoBuilder apiInfoBuilder=new ApiInfoBuilder();
        apiInfoBuilder.title("PM后端接口文档")
                .description("PM项目接口规范和说明")
                .version("1.0.0")
                .contact(new Contact("xzp","pm","civilizationfor@outlook.com"))
                .build();
        ApiInfo apiInfo= apiInfoBuilder.build();
        docket.apiInfo(apiInfo)//封面信息，标题，版本，作者
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.thf.controller"))
                .paths(PathSelectors.any())//regex("/users/")
                .build();
        return docket;
    }
}
