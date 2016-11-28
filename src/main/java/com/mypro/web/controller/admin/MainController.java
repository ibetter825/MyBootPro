package com.mypro.web.controller.admin;

import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class MainController extends BaseAdminController {
	@RequestMapping("/main")
	public ModelAndView index(Map<String, String> model){
		model.put("name", "这里是主页面!");
		return new ModelAndView("admin/main", model);
	}
	@RequestMapping("/role")
	@PreAuthorize("hasAuthority('admin')")
	public String role(Map<String, String> model){
		return "拥有权限";
	}
}
