package com.lcl.springlearning;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class B {
	
	@Pointcut("execution(* doing(..))")
	public void pointCutMethod(){
	}
	
	@Before("pointCutMethod()")
	public void doBefore(){
		System.out.println("前置通知！");
	}
	
	@After("pointCutMethod()")
	public void doAfter(){
		System.out.println("后置通知！");
	}
}
