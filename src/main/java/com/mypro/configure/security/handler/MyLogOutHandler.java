package com.mypro.configure.security.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
/**
 * 使用 spring security 注销成功后做的操作
 * @author user
 *
 */
public class MyLogOutHandler implements LogoutHandler {

    @Override
	public void logout(HttpServletRequest req, HttpServletResponse resp, Authentication auth) {
    	System.err.println("登出");
	} 

}
