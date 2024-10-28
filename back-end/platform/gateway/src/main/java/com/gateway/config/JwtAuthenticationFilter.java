package com.gateway.config;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

// "WebFilter" en Spring Framework es parte del módulo Spring WebFlux y se utiliza para implementar filtros que se aplican a las solicitudes
// y respuestas HTTP en una aplicación web reactiva. Proporciona una forma de interceptar y modificar las solicitudes y respuestas antes de
// que sean procesadas por los controladores y devueltas al cliente.
@Component
public class JwtAuthenticationFilter implements WebFilter { // "GatewayFilter" vs "WebFilter"

    @Override
    @NonNull
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        // "chain" representa la cadena de filtros
        // "exchange" es el objeto que representa la solicitud HTTP actual y sus metadatos
        return chain.filter(exchange);
    }

}
// "GatewayFilter" vs "WebFilter"
// Sí, puedes usar tanto GatewayFilter como WebFilter en Spring Cloud Gateway, pero tienen diferentes propósitos y momentos de ejecución en la cadena de filtros.
// - GatewayFilter: Se utiliza para definir filtros específicos para la puerta de enlace (gateway). Estos filtros se ejecutan antes de que la solicitud entre al sistema
//                  y, por lo general, se utilizan para realizar tareas de filtrado, manipulación o transformación en la solicitud entrante o la respuesta saliente.
//                  Los GatewayFilter son específicos de Spring Cloud Gateway.
// - WebFilter: Los WebFilter son filtros generales de Spring Web que se pueden utilizar en cualquier aplicación web Spring, incluida Spring Cloud Gateway. Estos filtros
//              se ejecutan en el nivel del servidor web subyacente y pueden utilizarse para realizar tareas de filtrado y manipulación en cualquier parte de la aplicación
//              web, no solo en la puerta de enlace.

// La elección entre GatewayFilter y WebFilter depende de tus necesidades específicas:
// - Si deseas realizar tareas de filtrado o manipulación específicas en tu puerta de enlace (gateway) antes de que las solicitudes se enruten a tus servicios, entonces
//   GatewayFilter es la elección adecuada. Puedes definir estos filtros específicos para tu puerta de enlace en la configuración de Spring Cloud Gateway.
// - Si deseas realizar tareas de filtrado o manipulación que son comunes a toda tu aplicación web, incluida la puerta de enlace y otros servicios, o si deseas ejecutar filtros
//   en una etapa posterior del procesamiento de solicitudes, entonces WebFilter es la elección adecuada. Puedes configurar estos filtros de manera global en tu aplicación Spring Boot.
// En resumen, ambas opciones son válidas, pero debes seleccionar la que se ajuste mejor a tus necesidades y al alcance de las tareas de filtrado o manipulación que deseas realizar
// en tu aplicación.