package com.nosqlcatalogs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@OpenAPIDefinition
@ComponentScan(basePackages = {"com.nosqlcatalogs", "com.shared.interceptors", "com.shared.exception", "com.shared.objectmapper"})
public class Z4nosqlCatalogsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(Z4nosqlCatalogsServiceApplication.class, args);
	}

}
