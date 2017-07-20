package com.linyi.service.mongo;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DBObject;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private MongoOperations template;
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String getSample() {
		DBObject english = new BasicDBObject().append("name","english").append("score", 5);
		template.getCollection("person").save(english);


		BasicDBList andList = new BasicDBList();
		andList.add(new BasicDBObject("name", "english"));
		andList.add(new BasicDBObject("score", 5)); 
		BasicDBObject and = new BasicDBObject("$and", andList);
		BasicDBObject command = new BasicDBObject("find", "person");
		command.append("filter", and); 
		CommandResult result = template.executeCommand(command);
		
		BasicDBObject list2 = (BasicDBObject)result.get("cursor");
		
		
		BasicDBList list = (BasicDBList)list2.get("firstBatch");
		for (int i = 0; i < list.size(); i ++) {
			System.out.println(list.get(i));
		}
		
		return "OK";
	}
	
	@RequestMapping(value = "/getAllCustomer", method = RequestMethod.GET)
	public List<Customer> getAllCustomer() {
		return customerRepository.findAll();
	}
	
	@RequestMapping(value = "/getCustomerByFirstname", method = RequestMethod.GET)
	public Customer getCustomerByFirstname(@RequestParam("firstname") String firstname) {
		return customerRepository.findByFirstName(firstname);
	}
	
	@RequestMapping(value = "/deleteCustomerById", method = RequestMethod.GET)
	public String deleteCustomerById(@RequestParam("cid") String cid) {
		customerRepository.delete(cid);
		return "OK";
	}
}
