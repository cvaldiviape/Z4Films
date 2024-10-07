package com.catalogs.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@OpenAPIDefinition
@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI customOpenAPI(@Value("${openapi.service.title}") String serviceTitle,
                                 @Value("${openapi.service.version}") String serviceVersion,
                                 @Value("${openapi.service.url}") String url) {
        // Server se utiliza para especificar la configuración del servidor donde se encuentra alojada tu API. Esto
        // incluye la URL base a la que los usuarios deben acceder para interactuar con la API. El Server es especialmente
        // útil cuando tienes múltiples entornos (por ejemplo, desarrollo, producción, pruebas) y deseas documentar cómo
        // acceder a la API en cada uno de estos entornos.
        Server server = new Server();
        server.url(url); // "http://localhost:8001"

        // Se define cómo se manejará la seguridad en la documentación de Swagger. En este caso, se especifica que
        // se utilizará un esquema de seguridad de tipo HTTP con el formato "bearer" (que es típico para tokens JWT).
        SecurityScheme securityScheme = new SecurityScheme();
        securityScheme.type(SecurityScheme.Type.HTTP);
        securityScheme.scheme("bearer"); // es necesario definir el schema "bearer", caso contrario colocara un valor
        // invalido como "sflsjhkfs" el swagger indicara que es un schema invalido
        securityScheme.bearerFormat("JWT"); // Si proporcionas un valor diferente a "JWT", estarías indicando que el
        // token de portador sigue un formato específico definido por ti o por tu
        // aplicación. En este caso, tendrías que asegurarte de que tanto el servidor
        // que emite el token como las partes que lo consumen entiendan y cumplan con
        // el formato especificado.

        String key = "bearerAuth";
        // Se define qué tipo de esquema de seguridad se utilizará en la API. En este caso "key" es una cadena que actúa
        // como un identificador para el esquema de seguridad "bearerAuth". "key" se utiliza para vincular el esquema de
        // seguridad con los endpoints de la API que requieren autenticación mediante un token JWT.
        Components components = new Components();
        components.addSecuritySchemes(key, securityScheme);

        // Se crea una instancia de SecurityRequirement, que indica que se requiere el esquema de seguridad bearerAuth
        // en las operaciones documentadas, los cuales son los "endpoints expuestos".
        SecurityRequirement securityRequirement = new SecurityRequirement();
        securityRequirement.addList(key); // especifica que los endpoints que requieren autenticación deben usar el esquema de seguridad "bearerAuth"

        // Se crea una instancia de Info, que define información general sobre la API, como su título y versión. Los valores
        // de título y versión se obtienen de las propiedades
        Info version = new Info();
        version.title(serviceTitle);
        version.version(serviceVersion);

        return new OpenAPI()
                .servers(List.of(server)) // Específica los servidores donde se aloja la API.
                .components(components) // Define los componentes utilizados en la documentación de Swagger, como los esquemas de seguridad.
                .security(List.of(securityRequirement)) // Configura los requisitos de seguridad para las operaciones que requieren autenticación.
                .info(version); // Define la información general de la API, como su título y versión.
    }

}