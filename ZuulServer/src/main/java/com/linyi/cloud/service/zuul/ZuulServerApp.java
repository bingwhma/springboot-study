package com.linyi.cloud.service.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableZuulProxy
@SpringBootApplication
@RestController
public class ZuulServerApp {
	
	@RequestMapping("/index")  
	public Object index() {  
	    return "index";  
	}  
	  
	@RequestMapping("/home")  
	public Object home() {  
	    return "home";  
	}
	
	public static void main(String[] args) {
		
		SpringApplication.run(ZuulServerApp.class, args);
	}
	
	@Bean
	public AccessLogFilter accessFilter() {
		return new AccessLogFilter();
	}
}
