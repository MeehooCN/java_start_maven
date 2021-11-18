package com.meehoo.biz.mobile.config;

import com.google.common.base.Predicates;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by CZ on 2018/7/17.
 *当application.properties 的swagger.enable = false时禁用swagger
 */
@ConditionalOnProperty(prefix = "swagger", value = {"enable"}, havingValue = "true")
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket customDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("mobile-module")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())// 对所有api进行监控
                .paths(Predicates.not(PathSelectors.regex("/error.*")))//SpringBoot默认的错误路径不监控
                .build();

    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("米弘科技", "http://localhost:8080", "meehoo@meehoo.cn");
        return new ApiInfoBuilder()
                .title("移动端后台")
                .description("Api接口说明")
                .contact(contact)
                .version("1.0.0")
                .build();
    }
}
