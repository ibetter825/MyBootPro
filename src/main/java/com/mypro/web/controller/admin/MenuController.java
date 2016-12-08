package com.mypro.web.controller.admin;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mypro.bean.constant.QueryConstant;
import com.mypro.bean.entity.SysMenu;
import com.mypro.bean.model.PageModel;
import com.mypro.bean.rq.PagerRq;
import com.mypro.bean.rq.QueryRq;
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
	
	/**
	 * 查询所有菜单
	 * @return
	 */
	@RequestMapping(value="/menu/list")
	public PageModel list(PagerRq page, QueryRq query){
		Page<Map> pager = PageHelper.startPage(page.getPage(), page.getSize());//分页插件
		PageHelper.orderBy(query.getOrder());
		sysMenuService.queryWithParams(query);
		return new PageModel(pager);
	}
}
