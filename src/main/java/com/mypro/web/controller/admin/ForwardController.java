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
	@RequestMapping("/forward/{view}")
	public ModelAndView index(@PathVariable("view") String view){
		return new ModelAndView("admin/"+view);
	}
}