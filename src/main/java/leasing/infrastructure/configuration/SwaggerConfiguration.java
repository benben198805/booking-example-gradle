package leasing.infrastructure.configuration;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
@Data
@RequiredArgsConstructor
public class SwaggerConfiguration {

    private static final String API_NAME = "TW LEASING DEMO API";
    private static final String API_DESCRIPTION = "TW LEASING DEMO API";
    private static final String API_VERSION = "1.0.0";
    private static final String EMAIL = "";
    private static final String GROUP_NAME = "API V1";
    private static final String BASE_PACKAGE = "tw.leasing.demo";

    @Value("${swagger.enable}")
    private Boolean enableSwagger;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(enableSwagger)
                .groupName(GROUP_NAME)
                .select()
                .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .securityContexts(securityContexts());
    }

    private List<SecurityContext> securityContexts() {
        List<SecurityContext> securityContexts = new ArrayList<>();
        securityContexts.add(
                SecurityContext.builder()
                               .securityReferences(defaultAuth())
                               .build());
        return securityContexts;
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> securityReferences = new ArrayList<>();
        securityReferences.add(new SecurityReference("Authorization", authorizationScopes));
        securityReferences.add(new SecurityReference("secret-key", authorizationScopes));
        return securityReferences;
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(API_NAME, API_DESCRIPTION, API_VERSION, "",
                new Contact("", "", EMAIL), "", "", Collections.emptyList());
    }
}
