package com.linyi.cloud.service;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ULLogAspect {

	@Pointcut("@annotation(com.linyi.cloud.service.UILog)")
	public void annotationPointCut() {
		
	}
	
	@Before("annotationPointCut()")
	public void before(JoinPoint joinPoint) {
		
		System.out.println("============= BEFORE ==============");
		Object[] obj = joinPoint.getArgs();
		
		
		MethodSignature sign = (MethodSignature)joinPoint.getSignature();
		Method method = sign.getMethod();
		String[] args = sign.getParameterNames();
		
		System.out.print("===========" + method.getName() + "=====");
		System.out.println(args[0] + ":" + obj[0] + "  " + args[1] + ":" + obj[1]);
		
	}
	
}
