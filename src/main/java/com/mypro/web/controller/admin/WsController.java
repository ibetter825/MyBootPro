package com.mypro.web.controller.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
//@Scope("prototype") //每次请求生成一个controller，改变默认的单例模式
@RequestMapping("/ws")
public class WsController {
	@RequestMapping("")
	public ModelAndView index(){
		return new ModelAndView("ws/ws");
	}
}
