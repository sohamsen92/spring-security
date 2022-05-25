package com.eazybytes.springsection2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;


@SpringBootApplication
@ComponentScans({ @ComponentScan("com.eazybytes.controller"), @ComponentScan("com.eazybytes.config") })

public class Springsection2Application {

	public static void main(String[] args) {
		SpringApplication.run(Springsection2Application.class, args);
	}

}
