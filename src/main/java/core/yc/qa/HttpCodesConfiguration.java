package core.yc.qa;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author limit (Yurii Chukhrai)
 */
@Configuration
public class HttpCodesConfiguration {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("HTTP status code demo.")
                        .description("HTTP status codes demo API. Spring Boot. Demo project for portfolio. Yurii Chukhrai.")
                        .version("0.0.6-SNAPSHOT")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Author GitHub")
                        .url("https://github.com/YuriiChukhrai/http-status-codes-demo"));
    }

    //TODO ADD reading the [openapi.json]
}
