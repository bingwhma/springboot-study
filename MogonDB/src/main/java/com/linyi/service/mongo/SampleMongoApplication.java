package com.linyi.service.mongo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@SpringBootApplication
public class SampleMongoApplication implements CommandLineRunner {
	
	@Autowired
	private CustomerRepository repository;


	@Autowired
	private MongoOperations template;
	
	@Override
	public void run(String... args) throws Exception {
	
		
//		DBObject english = new BasicDBObject().append("name","english").append("score", 5);
//		template.getCollection("person").save(english);
		
		
//		this.repository.deleteAll();
//		
//		// save a couple of customers
//		this.repository.save(new Customer("Alice", "Smith"));
//		this.repository.save(new Customer("Bob", "Smith"));
//		
//		XXCustomer zz = new XXCustomer();
//		zz.setAge_(10);
//		zz.setSex_("1");
//		List ls = new ArrayList();
//		ls.add(zz);
//		
//		Customer tt = new Customer("Bob", "Smith");
//		tt.setXxxx("xxxx");
//		tt.setYyyy("yyyyyyy");
//		tt.setSubCust(ls);
//		
//		this.repository.save(tt);
//		
//		Customer zz2 = new Customer();
//		zz2.setFirstName("1111");
//		zz2.setLastName("222222");
//		List ls2 = new ArrayList();
//		ls2.add(zz2);
//		ls2.add(new Customer("Bob", "Smith"));
//		
//		Customer tt2 = new Customer("Bob", "Smith");
//		tt2.setSubCust(ls2);
//		
//		this.repository.save(tt2);
//		/**/
//
//		// fetch all customers
//		System.out.println("Customers found with findAll():");
//		System.out.println("-------------------------------");
//		for (Customer customer : this.repository.findAll()) {
//			System.out.println(customer);
//		}
//		System.out.println();
//
//		// fetch an individual customer
//		System.out.println("Customer found with findByFirstName('Alice'):");
//		System.out.println("--------------------------------");
//		System.out.println(this.repository.findByFirstName("Alice"));
//
//		System.out.println("Customers found with findByLastName('Smith'):");
//		System.out.println("--------------------------------");
//		for (Customer customer : this.repository.findByLastName("Smith")) {
//			System.out.println(customer);
//		}
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SampleMongoApplication.class, args);
	}
}
