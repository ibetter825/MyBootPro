package com.mypro.web.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mypro.bean.constant.SecurityConstant;
import com.mypro.common.utils.WebUtil;

@Controller
public class LoginController extends BaseAdminController {
	
	@RequestMapping("/login")
	public ModelAndView login(){
		return new ModelAndView("admin/login");
	}
	@RequestMapping("/logout")
	public ModelAndView logout(){
		return new ModelAndView("admin/login");
	}
	@RequestMapping("/timeout")
	public ModelAndView timeout(){
		if(WebUtil.isAjax(request))
			WebUtil.writeJson(response, SecurityConstant.TIME_OUT_SESSION_MSG);
		return new ModelAndView("admin/error/timeout");
	}
	@RequestMapping("/expired")
	public ModelAndView expired(){
		if(WebUtil.isAjax(request))
			WebUtil.writeJson(response, SecurityConstant.EXPIRED_SESSION_MSG);
		return new ModelAndView("admin/error/expired");
	}
}
