package com.mypro.web.controller.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
/**
 * 工作台
 * @author ibett
 *
 */
@RestController
public class DeskController extends BaseAdminController {
	@RequestMapping("/desk")
	public ModelAndView index(){
		return new ModelAndView("admin/desk");
	}
}
