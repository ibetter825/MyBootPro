package com.mypro.service.admin;

import java.util.List;
import java.util.Map;

import com.mypro.bean.entity.admin.SysMenu;
import com.mypro.bean.rq.QueryRq;
import com.mypro.configure.security.customer.MyUserDetails;

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
	 * 查询用户的所有菜单
	 * @param userId
	 * @return
	 */
	public List<SysMenu> queryMenus(MyUserDetails user);
	/**
	 * 通过参数查询菜单
	 * @return
	 */
	public List<Map<String, Object>> queryWithParams(QueryRq query);
	/**
	 * 新增或修改
	 * @param menu
	 * @return
	 */
	public boolean addOrEdit(SysMenu menu);
	/**
	 * 批量删除数据
	 * @param idArr
	 * @return
	 */
	public List<Integer> batchRemove(SysMenu[] menus);
	public List<?> queryBySql();
}
