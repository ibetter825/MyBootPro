package com.mypro.dao.admin;

import java.util.List;
import java.util.Map;

import com.mypro.bean.entity.admin.SysMenu;
import com.mypro.dao.BaseDao;

/**
 * 系统菜单 dao
 * @author user
 *
 */
public interface SysMenuDao extends BaseDao<SysMenu> {
	/**
	 * 通过参数查询
	 * @param rq
	 * @return
	 */
	public List<Map<String, Object>> selectWithParams(Map<String, Object> rq);
	/**
	 * 传入('1,2,3,4')格式的菜单id查询
	 * @param menuIds
	 * @return
	 */
	public List<SysMenu> selectWithMenuIds(Map<String, String> rq);
}
