package com.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity // Esta anotación habilita la configuración de seguridad basada en WebFlux
public class SecurityConfiguration {

    @Autowired // OK
    private JwtAuthenticationFilter authenticationFilter; // implemente la interface "WebFilter" para agregar el token a cada request que realice a los servicios (propagar el token)

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity serverHttpSecurity) { // crea y configura un objeto SecurityWebFilterChain que representa la cadena de filtros de seguridad de WebFlux.
        return serverHttpSecurity
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .addFilterBefore(authenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/v3/api-docs/**", "/swagger-ui/**", "/actuator/health",
                                "/z4-master-data-service/v3/api-docs/**",
                                "/z4-catalogs-service/v3/api-docs/**",
                                "/z4-studios-service/v3/api-docs/**",
                                "/z4nosql-catalogs-service/v3/api-docs/**")
                        .permitAll()
                        .anyExchange()
                        .authenticated())
                .oauth2ResourceServer((oauth) -> oauth.jwt(Customizer.withDefaults()))
                .build();
    }

    // csrf(ServerHttpSecurity.CsrfSpec::disable): Deshabilita la protección CSRF (Cross-Site Request Forgery) en la aplicación. Esto puede ser necesario
    // en aplicaciones RESTful y reactivas donde las solicitudes no siguen un flujo de navegación tradicional.

    // authorizeExchange(exchange -> exchange.anyExchange().authenticated()): Esta línea define la regla de autorización. Significa que cualquier intercambio
    // (cualquier solicitud) debe estar autenticado para acceder a cualquier recurso. En otras palabras, se requiere autenticación para todas las solicitudes.

    // oauth2ResourceServer((oauth) -> oauth.jwt(Customizer.withDefaults())): Configura el servidor de recursos OAuth2 y especifica que se debe usar el formato
    // JWT (JSON Web Token) para autenticación. La llamada a Customizer.withDefaults() configura el servidor con las opciones predeterminadas para JWT.
}