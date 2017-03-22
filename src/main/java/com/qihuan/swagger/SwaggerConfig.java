package com.qihuan.swagger;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Profile("dev")
//@ComponentScan
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                    .select()
                    .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                    .paths(PathSelectors.any())
                .build()
                .globalResponseMessage(RequestMethod.GET,
                        Lists.newArrayList(new ResponseMessageBuilder().code(500).message("500 ERROR").build()));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("用户管理系统")
                .description("Restful API文档")
                .contact(new Contact("QiHuan","https://github.com/Qi-Huan/awesome", "qihuan92@126.com"))
                .version("1.0")
                .build();
    }
}