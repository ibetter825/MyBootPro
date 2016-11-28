package com.mypro.web.interceptor.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 后台用户登录权限拦截
 * @author user
 *
 */
public class AuthSecurityInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest res, HttpServletResponse resp, Object obj, Exception ex)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest res, HttpServletResponse resp, Object obj, ModelAndView mv)
			throws Exception {
		
	}

	@Override
	public boolean preHandle(HttpServletRequest res, HttpServletResponse resp, Object obj) throws Exception {
		System.err.println("---------进入拦截器");
		return true;
	}

}
