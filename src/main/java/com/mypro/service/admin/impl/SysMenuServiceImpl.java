package com.mypro.service.admin.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.mypro.bean.entity.SysMenu;
import com.mypro.bean.rq.QueryRq;
import com.mypro.dao.admin.SysMenuDao;
import com.mypro.service.admin.SysMenuService;

@Service
public class SysMenuServiceImpl implements SysMenuService {

	@Autowired
	private SysMenuDao sysMenuDao;

	@Override
	//@Cacheable(value = "system", key = "'menus'")
	public List<SysMenu> queryMenus() {
		return sysMenuDao.selectAll();
	}
	public List<Map<String, Object>> queryWithParams(QueryRq query){
		return sysMenuDao.selectWithParams(query.getRq());
	}
	public List<Map<String, Object>> queryBySql(){
		String sql = "select menu_id, menu_name from sys_menu";
		return sysMenuDao.selectBySql(sql);
	}
}
