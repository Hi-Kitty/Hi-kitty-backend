package io.nuabo.openapi;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(info = @Info(
        title = "Hi Kitty 프로젝트!!",
        version = "v1",
        description = "Hi Kitty의 API 명세서입니다."
),
servers = {
        @io.swagger.v3.oas.annotations.servers.Server(
                description = "Local Server",
                url = "http://localhost:8080"
        ),
        @io.swagger.v3.oas.annotations.servers.Server(
                description = "Dev Server",
                url = "http://hikitty.ap-northeast-2.elasticbeanstalk.com"
        ),
        @io.swagger.v3.oas.annotations.servers.Server(
                description = "Prod Server",
                url = "https://www.nanum.site"
        )
})
@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("Bearer Authentication",
                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
    }
}
