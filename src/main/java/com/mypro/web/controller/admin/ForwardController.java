package com.mypro.web.controller.admin;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 跳转页面
 * @author ibett
 *
 */
@RestController
public class ForwardController extends BaseAdminController {
	
	/**
	 * 跳转到页面
	 * @param view
	 * @return
	 */
	@RequestMapping("/forward/{view}.html")
	public ModelAndView index(@PathVariable("view") String view){
		return new ModelAndView("admin/"+view);
	}
	
	/**
	 * 跳转到模板页面
	 * menu为传入的菜单id
	 * @param menuId
	 * @return
	 */
	@RequestMapping("/forward/tpl/{menu}.html")
	public ModelAndView tpl(@PathVariable("menu") Integer menu){
		return new ModelAndView("admin/tpl");
	}
}
