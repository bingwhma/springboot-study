package com.linyi.cloud.service;

import org.springframework.stereotype.Component;

import com.linyi.cloud.aspectj.UILog;

@Component
public class UserService {

	@UILog
	public int add(int a, int b) {
		return a + b;
	}
}
