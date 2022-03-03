package com.example.demo.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author 温黎明
 * @version 1.0
 * @date 2022/2/27 10:36
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket webApiConfig(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("webApi").apiInfo(webApiInfo()).select().build();
    }

    private ApiInfo webApiInfo(){
        return new ApiInfoBuilder().title("rabbitmq接口文档").
                description("本文档描述了rabbitmq微服务接口定义").
                version("1.0").
                contact(new Contact("nejoy6288","http://www.baidu.com","2221123@qq.com")).build();
    }
}
