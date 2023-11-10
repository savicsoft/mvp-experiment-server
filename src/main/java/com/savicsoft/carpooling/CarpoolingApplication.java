package com.savicsoft.carpooling;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Carpooling application",
				description = "This Carpooling application is designed to revolutionize the way people commute",
				version = "v1.0",
				contact = @Contact(
						name="Savicsoft",
						email = "sasa@savicsoft.com",
						url = "https://savicsoft.com/"
				),
				license = @License(
						name = "This carpooling application is distributed under the 'Savicsoft Carpooling License,' which restricts redistribution and usage for commercial purposes. Please contact us for licensing details.",
						url = "https://savicsoft.com/"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Our LinkedIn page",
				url = "https://www.linkedin.com/company/savicsoft/"
		)
)
public class CarpoolingApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarpoolingApplication.class, args);
	}

}
