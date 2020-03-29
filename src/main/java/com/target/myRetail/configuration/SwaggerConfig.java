package com.target.myRetail.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
          .select()                                  
          .apis(RequestHandlerSelectors.basePackage("com.target.myRetail"))
          .paths(PathSelectors.any())
          .build();
    }
//
//    private ApiInfo apiEndPointsInfo() {
//        return new ApiInfoBuilder().title("My Retail")
//                .description("My Retail REST API")
//                .contact(new Contact("Sphoorthi Gaddam", "", "example@gmail.com"))
//                .version("1.0.0")
//                .build();
//    }
}