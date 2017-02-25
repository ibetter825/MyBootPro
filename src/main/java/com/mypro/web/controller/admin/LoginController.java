package com.mypro.web.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
		return new ModelAndView("admin/timeout");
	}
	@RequestMapping("/expired")
	public ModelAndView expired(){
		return new ModelAndView("admin/expired");
	}
}
