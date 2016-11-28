package com.mypro.web.controller.wap;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class IndexController extends BaseWapController {
	@RequestMapping("/")
	public ModelAndView index(){
		return new ModelAndView("wap/index");
	}
	
}
