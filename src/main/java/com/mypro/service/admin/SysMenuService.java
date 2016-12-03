package com.mypro.service.admin;

import java.util.List;

import com.mypro.bean.entity.SysMenu;

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
	public List queryBySql();
}
