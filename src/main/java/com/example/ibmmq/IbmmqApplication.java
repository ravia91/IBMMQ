package com.example.ibmmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class IbmmqApplication {

	public static void main(String[] args) {
		SpringApplication.run(IbmmqApplication.class, args);
	}

}
