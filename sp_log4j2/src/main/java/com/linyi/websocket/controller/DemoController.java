package com.linyi.websocket.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

	public static final Logger logger = LogManager.getLogger(DemoController.class.getName());
	
    @GetMapping("index")
    public ResponseEntity<String> index(){
    	logger.info("----------call index---------------------");
        return ResponseEntity.ok("请求成功");
    }


}