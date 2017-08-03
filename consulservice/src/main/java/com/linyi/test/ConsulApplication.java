package com.linyi.test;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication  
@EnableDiscoveryClient  
@RestController
public class ConsulApplication {
	
	@RequestMapping("/hi")
	public String home() {
		return "hello 8503";
	}

	public static void main(String[] args) {
		new SpringApplicationBuilder(ConsulApplication.class).web(true).run(args);
	}
}
