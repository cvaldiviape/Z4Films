package com.master;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.master", "com.shared.interceptors", "com.shared.exception"})
public class MasterDataServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MasterDataServiceApplication.class, args);
	}

}
