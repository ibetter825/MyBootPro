package com.mypro.web.controller.admin;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.mypro.bean.entity.SysMenu;
import com.mypro.service.admin.SysMenuService;

@RestController
public class MenuController extends BaseAdminController {
	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(MainController.class);
	@Autowired
	private SysMenuService sysMenuService;
	
	/**
	 * 查询菜单树
	 * @return
	 */
	@RequestMapping(value="/menu/tree", method = RequestMethod.POST)
	public List<Map<String, Object>> tree(){
		return SysMenu.getMenuTree(sysMenuService.queryMenus(), null);
	}
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/menu/list")
	public List list(){
		return sysMenuService.queryBySql();
	}
}
