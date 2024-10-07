package com.shared.interceptors;

import com.shared.constants.SecurityConstans;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class InputInterceptorFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        viewUrl(request);
        viewToken(request);
        filterChain.doFilter(request, response);
    }

    private void viewUrl(HttpServletRequest request) {
        StringBuffer url = request.getRequestURL();
        String urlString = url.toString();
        System.out.println("URL: " + urlString);
    }

    private void viewToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(SecurityConstans.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith(SecurityConstans.BEARER)) {
            String token = authorizationHeader.substring(7); // Elimina "Bearer " para obtener el token real
            System.out.println("Token de acceso: " + token);
        }
    }

}