package com.savicsoft.carpooling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.savicsoft.carpooling")
public class CarpoolingApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarpoolingApplication.class, args);
	}

}
