package com.mypro.configure.druid.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import com.alibaba.druid.support.http.StatViewServlet;

/**
 * (通过注解建立servlet，与在web.xml中配置一样)
 * @author user
 *
 */
@WebServlet(urlPatterns="/druid/*",  
initParams={  
     @WebInitParam(name="allow",value="127.0.0.1"),// IP白名单(没有配置或者为空，则允许所有访问，多个","分割)
     @WebInitParam(name="deny",value="192.168.1.73"),// IP黑名单 (存在共同时，deny优先于allow)  
     @WebInitParam(name="loginUsername",value="admin"),// 用户名  
     @WebInitParam(name="loginPassword",value="123456"),// 密码  
     @WebInitParam(name="resetEnable",value="false")// 禁用HTML页面上的“Reset All”功能  
}) 
public class DruidStatViewServlet extends StatViewServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		System.err.println("---------初始化DruidStatViewServlet:访问 http://127.0.0.1/druid/api.html");
		super.init();
	}
	
}
