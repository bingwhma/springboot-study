package com.linyi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.linyi.config.Page;
import com.linyi.domain.User;
import com.linyi.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/page/{pageNo}", method = RequestMethod.GET)
	public List<User> page(@PathVariable(value = "pageNo") int pageNo) {
		Page<User> p = new Page<User>();
		p.setPageNo(pageNo);
		p.setPageSize(10);
		
		return userService.page(p);
	}
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<User> all() {
		return userService.findAll();
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.GET)
	public String save() throws Exception {
		userService.save();
		return "save ok!!";
	}

	@RequestMapping(value = "/{name}/name", method = RequestMethod.GET)
	public List<User> likeName2(@PathVariable(value = "name") String name) {
		return userService.likeName(name);
	}
}
