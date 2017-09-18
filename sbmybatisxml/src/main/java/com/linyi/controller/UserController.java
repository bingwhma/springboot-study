package com.linyi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.linyi.dao.UserDao;
import com.linyi.domain.User;

@RestController
public class UserController {

	@Autowired
	private UserDao user;

	@RequestMapping(value = "/findbyid", method = RequestMethod.GET)
	public User findById(@RequestParam(value = "id", required = true) long id) {
		return user.selectUserById(id);
	}

	@RequestMapping(value = "/{id}/id", method = RequestMethod.GET)
	public User findByIdPath(@PathVariable(value = "id") long id) {
		return user.selectUserById(id);
	}
}
