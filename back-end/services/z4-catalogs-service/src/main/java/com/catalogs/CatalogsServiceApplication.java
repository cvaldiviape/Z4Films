package com.catalogs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@OpenAPIDefinition
@EnableFeignClients
@ComponentScan(basePackages = {"com.catalogs", "com.shared.interceptors", "com.shared.exception"})
public class CatalogsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatalogsServiceApplication.class, args);
	}

}