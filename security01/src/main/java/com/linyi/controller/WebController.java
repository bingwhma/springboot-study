package com.linyi.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {
	
	@RequestMapping(value="/security", method = RequestMethod.GET)  
    public String security() {  
        return "hello world security";  
    }
	
	@RequestMapping(value="/admin", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String admin() {  
        return "role admin";  
    }
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
		System.out.println("================logout=================");
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    return "redirect:/hello";//You can redirect wherever you want, but generally it's a good practice to show login screen again.
	}
	
//	@RequestMapping("/logout")  
//  public String logout(HttpServletRequest request) {
//		request.getSession().invalidate();
//		
//      return "logout";  
//  } 
	
	
	@RequestMapping("/hello")  
    public String hello() {  
        return "不验证哦";  
    }
	
 

}
