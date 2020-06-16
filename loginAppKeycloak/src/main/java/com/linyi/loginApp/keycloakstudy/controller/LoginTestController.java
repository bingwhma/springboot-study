package com.linyi.loginApp.keycloakstudy.controller;

import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginTestController {

	@GetMapping(path = "/products")
    public String getProducts(Model model){
        model.addAttribute("products", Arrays.asList("test001","test002","test003"));
        return "product";
    }

    @GetMapping(path = "/logout")
    public String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return "/";
    }
}
