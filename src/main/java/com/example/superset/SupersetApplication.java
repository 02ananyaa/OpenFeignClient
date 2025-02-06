package com.example.superset;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class SupersetApplication {

	public static void main(String[] args) {
		SpringApplication.run(SupersetApplication.class, args);
	}

}
