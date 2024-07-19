package org.depromeet.spot.application.common.config;

import java.util.List;

import jakarta.servlet.ServletContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(ServletContext servletContext) {
        String contextPath = servletContext.getContextPath();
        Server server = new Server().url(contextPath);
        return new OpenAPI()
                .servers(List.of(server))
                .addSecurityItem(new SecurityRequirement().addList("accessToken"))
                .components(authSetting())
                .info(swaggerInfo());
    }

    private Info swaggerInfo() {
        return new Info()
                .version("v0.0.1")
                .title("야구장 좌석 시야 서비스, SPOT API 문서")
                .description("SPOT 서버의 API 문서입니다.");
    }

    private Components authSetting() {
        return new Components()
                .addSecuritySchemes(
                        "accessToken",
                        new SecurityScheme()
                                .type(Type.HTTP)
                                .scheme("Bearer")
                                .bearerFormat("JWT")
                                .in(In.HEADER)
                                .name("Authorization"));
    }
}
