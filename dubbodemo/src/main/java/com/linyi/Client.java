package com.linyi;

import java.util.Date;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.linyi.service.DemoServer;

public class Client {

	public static void main(String[] args) {

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "applicationConsumer.xml" });
		context.start();
		
		System.out.println("===================================================");
		DemoServer demoServer = (DemoServer) context.getBean("demoService");
		
		System.out.println("client:"
				+ demoServer.sayHello("Morning" + "1:" + new Date()) + "3:"
				+ new Date());
		
		System.out.println("===================================================");
	}

}
