package com.mypro.service.admin;

import java.util.List;
import java.util.Map;

import com.mypro.bean.entity.admin.SysMenu;
import com.mypro.bean.rq.QueryRq;

/**
 * 系统菜单 service接口
 * @author user
 *
 */
public interface SysMenuService {
	/**
	 * 查询所有的菜单
	 * @return
	 */
	public List<SysMenu> queryMenus();
	/**
	 * 通过参数查询菜单
	 * @return
	 */
	List<Map<String, Object>> queryWithParams(QueryRq query);
	public List queryBySql();
}
