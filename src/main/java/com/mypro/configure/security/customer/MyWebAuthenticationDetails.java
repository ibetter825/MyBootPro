package com.mypro.configure.security.customer;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.WebAuthenticationDetails;
/**
 * 用于新增登录表单参数
 * @author user
 *
 */
public class MyWebAuthenticationDetails extends WebAuthenticationDetails {

	private static final long serialVersionUID = 1L;

	//验证码,需从前台传递过来
	private final String captcha;
	
	public MyWebAuthenticationDetails(HttpServletRequest request) {
		super(request);
		captcha = request.getParameter("captcha");
	}

	public String getCaptcha() {
		return captcha;
	}
}
