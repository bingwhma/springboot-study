package com.linyi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.linyi.configure.Page;
import com.linyi.dao.UserDao;
import com.linyi.domain.User;

@RestController
public class UserController {

	@Autowired
	private UserDao user;

	@RequestMapping(value = "/page/{pageNo}", method = RequestMethod.GET)
	public List<User> page(@PathVariable(value = "pageNo") int pageNo) {
		Page<User> p = new Page<User>();
		p.setPageNo(pageNo);
		p.setPageSize(10);
		
		return user.findAll(p);
	}
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<User> all() {
		return user.findAll();
	}

	@RequestMapping(value = "/{id}/id", method = RequestMethod.GET)
	public User findByIdPath(@PathVariable(value = "id") long id) {
		return user.selectUserById(id);
	}
}
