package com.linyi.websocket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Lg4j2Application {

	public static final Logger logger = LogManager.getLogger(Lg4j2Application.class.getName());
	
	public static void main(String[] args) {
		SpringApplication.run(Lg4j2Application.class, args);
		
		logger.info("------------start----------------");

	}

}
