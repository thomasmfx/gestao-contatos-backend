package com.comerciosa.gestao_contatos.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Gestão de Contatos")
                        .version("1.0")
                        .description("Documentação da API de clientes e contatos"))
                .tags(List.of(
                        new Tag().name("Clientes").description("Gestão de clientes"),
                        new Tag().name("Contatos").description("Gestão de contatos")
                ));
    }
}