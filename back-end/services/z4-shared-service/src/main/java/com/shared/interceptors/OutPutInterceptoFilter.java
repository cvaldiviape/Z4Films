package com.shared.interceptors;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class OutPutInterceptoFilter implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken) {
            addTokenInRequest(request, (JwtAuthenticationToken) authentication);
        }
        viewUrl(request);
    }

    private void addTokenInRequest(RequestTemplate request, JwtAuthenticationToken authentication) {
        Jwt token = authentication.getToken();
        if (token != null) { // si existe el token se seteare en el header del request saliente
            String tokenValue = token.getTokenValue();
            request.header("Authorization", "Bearer " + tokenValue);
            String url = request.url();
            System.out.println("URL REQUEST: " + url);
        }
    }

    private void viewUrl(RequestTemplate request) {
        String url = request.feignTarget().url();
        System.out.println("URL: " + url);
    }

}