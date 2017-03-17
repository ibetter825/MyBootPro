package com.mypro.configure.security.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.alibaba.fastjson.JSON;
import com.mypro.bean.model.ResultModel;
import com.mypro.common.utils.WebUtil;
/**
 * 使用 spring security 登录成功后做的操作
 * @author user
 *
 */
public class MyLoginSuccessHandler implements AuthenticationSuccessHandler {

	private String defaultTargetUrl = "/admin/index.html";

	private boolean forwardToDestination = false; 
    
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy(); 
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication auth)
			throws IOException, ServletException {
		//MyUserDetails userDetails = (MyUserDetails) auth.getPrincipal(); //查询出来的用户 MyUserDetails.java
		if(WebUtil.isAjax(req))
			WebUtil.writeJson(resp, JSON.toJSONString(new ResultModel().setSuccessMsg(defaultTargetUrl)));
		else{
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
