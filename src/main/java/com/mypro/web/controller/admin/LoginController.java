package com.mypro.web.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mypro.bean.constant.SecurityConstant;
import com.mypro.common.utils.WebUtil;

@Controller
public class LoginController extends BaseAdminController {
	
	@RequestMapping("/login.html")
	public ModelAndView login(){
		return new ModelAndView("admin/login");
	}
	@RequestMapping("/logout.html")
	public ModelAndView logout(){
		return new ModelAndView("admin/login");
	}
	@RequestMapping("/login/timeout.html")
	public ModelAndView timeout(){
		//可以在这里判断ajax后再返回null，但是仍然有问题，最好的办法还是直接覆盖其拦截器的设置
		if(WebUtil.isAjax(request))
			WebUtil.writeJson(response, SecurityConstant.TIME_OUT_SESSION_MSG);
		return new ModelAndView("admin/error/timeout");
	}
	@RequestMapping("/login/expired.html")
	public ModelAndView expired(){
		if(WebUtil.isAjax(request))
			WebUtil.writeJson(response, SecurityConstant.EXPIRED_SESSION_MSG);
		return new ModelAndView("admin/error/expired");
	}
}
