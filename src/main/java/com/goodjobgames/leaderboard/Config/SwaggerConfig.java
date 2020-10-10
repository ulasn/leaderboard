package com.goodjobgames.leaderboard.Config;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
@Import(springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    @Bean
    public Docket api(){
        Contact contact = new Contact(
                "Ulaş Nuhoğlu",
                "",
                "ulas.nuhoglu9@gmail.com"
        );

        List<VendorExtension> vext = new ArrayList<>();

        ApiInfo apiInfo = new ApiInfo(
                "Good Job Games",
                "Good Job Games Leaderboard Demo Project",
                "1.0.0",
                "",
                contact,
                "MIT",
                "",
                vext
        );

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .forCodeGeneration(true)
                .securityContexts(Lists.newArrayList(securityContext()))
                .securitySchemes(Lists.newArrayList(apiKey()));

        docket.select()
                .paths(PathSelectors.any())
                .build();
        return docket;
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", AUTHORIZATION_HEADER, "Authorization");
    }

    private SecurityContext securityContext(){
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any())
                .build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(
                new SecurityReference("JWT", authorizationScopes));
    }
}