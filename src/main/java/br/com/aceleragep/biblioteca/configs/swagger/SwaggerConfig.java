package br.com.aceleragep.biblioteca.configs.swagger;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

	@Bean
	public GroupedOpenApi publicApi() {
		return GroupedOpenApi.builder().group("br.com.aceleragep").pathsToMatch("/**").build();
	}

	@Bean
	public OpenAPI forumAluraOpenAPI() {
		return new OpenAPI().info(new Info().title("Biblioteca API")
				.description("Projeto de Documentacao da API da Biblioteca").version("v0.0.1"));
	}

}
