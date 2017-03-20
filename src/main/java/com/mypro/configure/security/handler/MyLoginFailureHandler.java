package com.mypro.configure.security.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.alibaba.fastjson.JSON;
import com.mypro.bean.model.ResultModel;
import com.mypro.common.utils.WebUtil;

/**
 * 登录失败后的处理方法
 * @author user
 *
 */
public class MyLoginFailureHandler implements AuthenticationFailureHandler {

	private String defaultTargetUrl = "/admin/login.html";

	private boolean forwardToDestination = false; 
    
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse resp, AuthenticationException auth)
			throws IOException, ServletException {
		System.out.println(auth.getMessage());//登录的错误信息

		if(WebUtil.isAjax(req))
			WebUtil.writeJson(resp, JSON.toJSONString(new ResultModel().setFailMsg(auth.getMessage())));
		else {
			if(this.forwardToDestination)
				req.getRequestDispatcher(this.defaultTargetUrl).forward(req, resp);  
	        else
	            this.redirectStrategy.sendRedirect(req, resp, this.defaultTargetUrl);
		}
	}
	
	public void setForwardToDestination(boolean forwardToDestination) {  
        this.forwardToDestination = forwardToDestination;  
    } 
}
