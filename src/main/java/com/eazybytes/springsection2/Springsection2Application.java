package com.eazybytes.springsection2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@ComponentScans({ @ComponentScan("com.eazybytes.controller"), @ComponentScan("com.eazybytes.config") })
@EnableJpaRepositories("com.eazybytes.repository")
@EntityScan("com.eazybytes.model")
public class Springsection2Application {

	public static void main(String[] args) {
		SpringApplication.run(Springsection2Application.class, args);
	}

}
