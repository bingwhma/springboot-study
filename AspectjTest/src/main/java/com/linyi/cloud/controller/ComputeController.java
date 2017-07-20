package com.linyi.cloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.linyi.cloud.service.UserService;

@RestController
public class ComputeController {

	@Autowired
	private UserService userService;

    @RequestMapping(value = "/add/{a}/{b}" ,method = RequestMethod.GET)
    public Integer add(@PathVariable Integer a, @PathVariable Integer b) {
        
        return userService.add(a, b);
    }

}
