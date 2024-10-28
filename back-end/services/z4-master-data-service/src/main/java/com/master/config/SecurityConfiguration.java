package com.master.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
// "/v3/api-docs/**", "/swagger-ui/**"

    @Value("${spring.security.oauth2.resourceserver.jwt.issuerUri}")
    private String issuerUri;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/z4-master-data-service/v3/api-docs/**", "/z4-master-data-service/swagger-ui/**")
                        .permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer((oauth2) -> oauth2.jwt(jwt -> {}))
                .build();
    }

    // Configurando el decodificador de tokens JWT para que valide tokens JWT emitidos por el servidor de autorización (Keycloak en este caso) que tiene la
    // ubicación especificada como emisor (issuer). En este caso, la URL del emisor es "http://localhost:9090/realms/z4movies-dev".
    @Bean
    public JwtDecoder jwtDecoder() {
        return JwtDecoders.fromIssuerLocation(this.issuerUri);
    }

    // documentacion: https://docs.spring.io/spring-security/reference/servlet/oauth2/resource-server/jwt.html
}