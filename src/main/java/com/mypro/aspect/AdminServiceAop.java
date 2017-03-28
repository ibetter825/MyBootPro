package com.mypro.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 业务层切面类
 * @author user
 *
 */
@Component
@Aspect
public class AdminServiceAop {
	/**
	 * 匹配com.mypro.service.admin.impl包及其子包下的所有类的所有方法  
	 */
	@Pointcut("execution(* com.mypro.service.admin.impl..*.query*(..))")
	public void executeService(){}
	
	/**
	 * 前置通知，方法调用前被调用 
	 * @param joinPoint
	 */
	@Before("executeService()")
	public void doBeforeAdvice(JoinPoint joinPoint){
		System.err.println("我是前置通知!!!");  
	    //获取目标方法的参数信息  
	    Object[] obj = joinPoint.getArgs();  
	    //AOP代理类的信息  
	    joinPoint.getThis();  
	    //代理的目标对象  
	    joinPoint.getTarget();  
	    //用的最多 通知的签名  
	    Signature signature = joinPoint.getSignature();  
	    //代理的是哪一个方法  
	    System.err.println(signature.getName());  
	    //AOP代理类的名字  
	    System.err.println(signature.getDeclaringTypeName());  
	}
}
