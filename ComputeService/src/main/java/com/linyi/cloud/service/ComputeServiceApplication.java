package com.linyi.cloud.service;

import org.apache.log4j.Logger;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

//@EnableDiscoveryClient
@SpringBootApplication
@EnableAspectJAutoProxy
public class ComputeServiceApplication {

	private static final Logger LOGGER = Logger.getLogger(ComputeServiceApplication.class);
	
	public static void main(String[] args) throws Exception {
		new SpringApplicationBuilder(ComputeServiceApplication.class).web(true).run(args);
		
		for (int i = 0; i < 50; i++) {
			LOGGER.info("========test==========");
			
			Thread.sleep(500);
		}
	}

}
