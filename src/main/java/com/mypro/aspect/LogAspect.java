package com.mypro.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.mypro.bean.entity.admin.SysOptLog;
import com.mypro.configure.security.customer.MyUserDetails;
import com.mypro.dao.admin.SysOptLogDao;

/**
 * 系统操作切面类
 * 参考资料: http://blog.csdn.net/zknxx/article/details/53240959
 * @author user
 *
 */
@Component
@Aspect
public class LogAspect {
	private final static String EXECUTIONS = "execution(* com.mypro.service.admin.impl..*.add*(..))"
			+ "||execution(* com.mypro.service.admin.impl..*.remove*(..))"
			+ "||execution(* com.mypro.service.admin.impl..*.edit*(..))"
			+ "||execution(* com.mypro.service.admin.impl..*.batch*(..))";
	@Autowired
	private SysOptLogDao logDao;

	/**
	 * 匹配com.mypro.service.admin.impl包及其子包下的"增删改"的方法
	 * execution的语法参考:http://www.tuicool.com/articles/z6Jzqu2
	 */
	@Pointcut(EXECUTIONS)
	public void executeService(){}
	
	/**
	 * 前置通知，方法调用前被调用 
	 * @param joinPoint
	 */
	@Before("executeService()")
	public void doBeforeAdvice(JoinPoint joinPoint){
		System.err.println("进入日志切面类前置通知!!!");  
	    //获取目标方法的参数信息  
	    Object[] args = joinPoint.getArgs(); 
	    //AOP代理类的信息  
	    //joinPoint.getThis();
	    //代理的目标对象  
	    //joinPoint.getTarget();
	    //用的最多 通知的签名  
	    Signature signature = joinPoint.getSignature();
	    //代理的是哪一个方法  
	    String method = signature.getName();
	    System.err.println("保存日志记录:"+(saveOptLog(method, args)?"成功":"失败"));
	    System.err.println(signature.getName());
	    //AOP代理类的名字  
	    System.err.println(signature.getDeclaringTypeName());
	}
	
	/** 
	 * 后置返回通知 
	 * 这里需要注意的是: 
	 *      如果参数中的第一个参数为JoinPoint，则第二个参数为返回值的信息 
	 *      如果参数中的第一个参数不为JoinPoint，则第一个参数为returning中对应的参数 
	 * returning 限定了只有目标方法返回值与通知方法相应参数类型时才能执行后置返回通知，否则不执行，对于returning对应的通知方法参数为Object类型将匹配任何目标返回值 
	 * @param joinPoint 
	 * @param keys 
	 */  
	@AfterReturning(value = EXECUTIONS, returning = "keys")  
	public void doAfterReturningAdvice1(JoinPoint joinPoint, Object keys){
		//操作完成后修改日志
	    System.err.println("后置返回通知的返回值："+keys);
	}
	
	/** 
	 * 后置异常通知 
	 *  定义一个名字，该名字用于匹配通知实现方法的一个参数名，当目标方法抛出异常返回后，将把目标方法抛出的异常传给通知方法； 
	 *  throwing 限定了只有目标方法抛出的异常与通知方法相应参数异常类型时才能执行后置异常通知，否则不执行， 
	 *      对于throwing对应的通知方法参数为Throwable类型将匹配任何异常。 
	 * @param joinPoint 
	 * @param exception 
	 */  
	@AfterThrowing(value = "executeService()", throwing = "exception")  
	public void doAfterThrowingAdvice(JoinPoint joinPoint, Throwable exception){
	    //目标方法名：  
	    System.err.println("发生异常"+joinPoint.getSignature().getName());
	    if(exception instanceof NullPointerException){
	        System.err.println("后置异常通知 : 发生了空指针异常!!!!!");  
	    }
	}
	
	/** 
	 * 环绕通知： 
	 *   环绕通知非常强大，可以决定目标方法是否执行，什么时候执行，执行时是否需要替换方法参数，执行完毕是否需要替换返回值。 
	 *   环绕通知第一个参数必须是org.aspectj.lang.ProceedingJoinPoint类型 
	 */  
	@Around(EXECUTIONS)
	public Object doAroundAdvice(ProceedingJoinPoint proceedingJoinPoint){
	    System.err.println("环绕通知的目标方法名："+proceedingJoinPoint.getSignature().getName());
	    try {
	        Object obj = proceedingJoinPoint.proceed();
	        return obj;
	    } catch (Throwable throwable) {
	        throwable.printStackTrace();
	    }  
	    return null;
	}
	
	/**
	 * 保存日志记录到数据库
	 * @param method
	 * @param args
	 * @return
	 */
	private boolean saveOptLog(String method, Object[] args){
		MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    SysOptLog log = new SysOptLog();
	    log.setOptDateTime(System.currentTimeMillis());
	    log.setOptUserId(userDetails.getUserId());
	    if(method.startsWith("batch"))//批量操作对象
	    	log.setOptLogCont("批量操作:"+JSON.toJSONString(args));
	    else if(method.startsWith("add"))
	    	log.setOptLogCont("编辑操作:"+JSON.toJSONString(args));
		//如果没存进去怎么整?
		return logDao.insertSelective(log) == 1;
	}
}
