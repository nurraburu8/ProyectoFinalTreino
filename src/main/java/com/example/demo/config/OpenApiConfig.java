package com.example.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Metadatos de la documentacion interactiva (Swagger UI), disponible en /docs.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI treinoOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("Treino API")
                .version("1.0")
                .description("""
                        API REST para la gestion de entrenadores personales, alumnos y resenas.

                        Las contrasenas se almacenan cifradas con BCrypt y nunca se devuelven en las respuestas.

                        Nota: esta version no incluye autenticacion por token; los endpoints son publicos.""")
                .contact(new Contact()
                        .name("Nicolas Urraburu")
                        .url("https://github.com/nurraburu8"))
                .license(new License().name("MIT")));
    }
}
