package com.mypro.dao.admin;

import java.util.List;
import java.util.Map;

import com.mypro.bean.entity.SysMenu;
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
}
