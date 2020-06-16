package com.linyi.jwt.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.linyi.jwt.config.JwtHelper;

@RestController
public class LoginController {

	@Autowired
    private JwtHelper jwtHelper;

    @PostMapping("/login")
    public Object login(String loginName,String password) {
        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("loginName", loginName);
        if ("123456".equals(password)) {
        	
        	JSONObject json = new JSONObject();
            json.put("status", 200);
            json.put("message", "成功");
            json.put("obj", jwtHelper.generateToken(claims));
            return json;
        } else {
        	
        	JSONObject json = new JSONObject();
            json.put("status", 200);
            json.put("message", "登录帐号或者登录密码错误");
            return json;
        }
    }
}
