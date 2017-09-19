package com.linyi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {
	
	@RequestMapping("/security")  
    public String security() {  
        return "hello world security";  
    }
	
	
	@RequestMapping("/hello")  
    public String hello() {  
        return "不验证哦";  
    }  

}
