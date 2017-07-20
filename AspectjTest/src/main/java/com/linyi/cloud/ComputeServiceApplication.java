package com.linyi.cloud;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
@ComponentScan(basePackages="com.linyi.cloud")
public class ComputeServiceApplication {

	public static void main(String[] args) throws Exception {
		new SpringApplicationBuilder(ComputeServiceApplication.class).web(true).run(args);
	}

}
