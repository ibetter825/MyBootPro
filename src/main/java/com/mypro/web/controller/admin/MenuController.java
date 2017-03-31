package com.mypro.web.controller.admin;

import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Maps;
import com.mypro.bean.entity.admin.SysMenu;
import com.mypro.bean.enums.ResultMessageEnum;
import com.mypro.bean.model.PageModel;
import com.mypro.bean.model.ResultModel;
import com.mypro.bean.rq.PagerRq;
import com.mypro.bean.rq.QueryRq;
import com.mypro.service.admin.SysMenuService;

@RestController
public class MenuController extends BaseAdminController {
	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(IndexController.class);
	@Autowired
	private SysMenuService sysMenuService;
	
	/**
	 * 查询用户菜单树菜单树
	 * @return
	 */
	@RequestMapping(value = "/menu/tree", method = RequestMethod.POST)
	public List<Map<String, Object>> tree(){
		return SysMenu.getMenuTree(sysMenuService.queryMenus(getCurrentUser()), null);
	}
	
	/**
	 * 查询所有菜单
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/menu/list")
	public PageModel list(PagerRq page, QueryRq query){
		/*
		 * 查询排序提供两种方法
		 * 1, grid插件使用page中的sort, order字段排序
		 * 2, 自定义查询使用query对象中的方法
		*/
		Page<Map> pager = PageHelper.startPage(page.getPage(), page.getSize());//分页插件
		PageHelper.orderBy(page.getOrder());
		sysMenuService.queryWithParams(query);
		return new PageModel(pager);
	}
	
	/**
	 * 新增或修改
	 * @param menu
	 * @return
	 */
	@RequestMapping(value = "/menu/aoe", method = RequestMethod.POST)
	public ResultModel addOrEdit(SysMenu dto){
		//提交表单时 还需要在服务端验证数据的合法性
		//...
		ResultModel model = null;
		if(sysMenuService.addOrEdit(dto))
			model = new ResultModel();
		else
			model = new ResultModel(ResultMessageEnum.OPTION_EXCEPTION);
		return model;
	}
	
	/**
	 * 删除/批量删除
	 * @return
	 */
	@RequestMapping(value = "/menu/del", method = RequestMethod.DELETE)
	public ResultModel del(String ids){
		ResultModel model = null;
		if(StringUtils.isNotEmpty(ids)){
			String[] strArr = ids.split(",");
			SysMenu[] menuArr = new SysMenu[strArr.length];
			SysMenu menu = null;
			for(int i = 0, l = strArr.length; i < l; i++){
				if(StringUtils.isNumeric(strArr[i])){
					menu = new SysMenu();
					menu.setMenuId(Integer.valueOf(strArr[i]));
					menu.setMenuState((short) -1);
					menuArr[i] = menu;
				}
			}
			//调用service
			List<Integer> errs = sysMenuService.batchRemove(menuArr);
			Map<String, Object> map = Maps.newHashMap();
			map.put("errs", errs);
			model = new ResultModel().setData(map);
		}else
			model = new ResultModel(ResultMessageEnum.PARAM_NOT_EMPTY);
		return model;
	}
}
