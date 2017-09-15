package com.linyi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.linyi.domain.User;
import com.linyi.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/name", method = RequestMethod.GET)
	public List<User> likeName(@RequestParam(value = "name", required = true) String name) {
		return userService.likeName(name);
	}

	@RequestMapping(value = "/{name}/name", method = RequestMethod.GET)
	public List<User> likeName2(@PathVariable(value = "name") String name) {
		return userService.likeName(name);
	}
}
