package com.linyi.cloud.aspectj;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class WebLogAspect {

	@Pointcut("execution(* com.linyi.cloud.controller..*.*(..))")
	public void execWeb(){  
		System.out.println("============= execWeb ==============");
	}
	
	@Before("execWeb()")  
	public void doBeforeAdvice(JoinPoint joinPoint) {
		System.out.println("============= doBeforeAdvice ==============");
	}
	
	@Around("execWeb()")
	public Object doAroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
		
		HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		System.out.println("==============" + req.getRemoteHost());
		System.out.println("==============" + req.getSession().getId());
		
		System.out.println("============= doAroundAdvice start==============");
		
		Object obj = proceedingJoinPoint.proceed();
		
		System.out.println("============= doAroundAdvice end ==============");
        return obj;
        
        
	}
		
}
