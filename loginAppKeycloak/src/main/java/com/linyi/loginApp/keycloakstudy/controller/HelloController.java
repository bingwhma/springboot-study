package com.linyi.loginApp.keycloakstudy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	
	@GetMapping("/products/hello")
  public String hello() {
      return "hello";
  }

  @GetMapping("/products/admin")
  public String admin(){
      return "admin";
  }
}
