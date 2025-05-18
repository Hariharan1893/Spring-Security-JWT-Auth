package com.auth.democode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringAuthenticationApplication {
	public static void main(String[] args) {

		SpringApplication.run(SpringAuthenticationApplication.class, args);
		System.out.println("Server started at port : 9090");
	}

}
