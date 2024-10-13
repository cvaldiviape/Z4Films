package com.studios;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@OpenAPIDefinition
@ComponentScan(basePackages = {"com.studios", "com.shared.interceptors", "com.shared.exception"})
public class StudiosServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudiosServiceApplication.class, args);
	}

}
