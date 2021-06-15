package com.caionastu.currencyconversion.common.configuration;

import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfiguration {

    public Docket swaggerDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("Currency Conversion")
                        .version("1.0.0")
                        .description("An App that convert two currencies with updated tax rate.")
                        .build())
                .enable(true)
                .select()
                .paths(PathSelectors.any())
                .build();
    }
}
