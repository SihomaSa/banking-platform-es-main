package com.acme.banking.platform;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(servers = {@Server(url = "/", description = "Default Server URL")})
@SpringBootApplication
public class BankingPlatformEsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankingPlatformEsApplication.class, args);
	}

}
