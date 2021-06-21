package com.caionastu.currencyconversion.common.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public Docket swaggerDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("Currency Conversion")
                        .version("1.0.0")
                        .description("An App that convert two currencies with updated tax rate.")
                        .contact(new Contact("Caio de Melo Nastulevitie", "https://github.com/caionastu", "caionastu@gmail.com"))
                        .build())
                .enable(true)
                .select()
                .paths(PathSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("com.caionastu.currencyconversion"))
                .build()
                .useDefaultResponseMessages(false);
    }
}
