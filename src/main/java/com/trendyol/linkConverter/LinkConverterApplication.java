package com.trendyol.linkConverter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class LinkConverterApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(LinkConverterApplication.class, args);
	}

}
