package com.mypro.service.admin.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mypro.bean.entity.admin.SysMenu;
import com.mypro.bean.rq.QueryRq;
import com.mypro.dao.admin.SysMenuDao;
import com.mypro.service.admin.SysMenuService;

@Service
public class SysMenuServiceImpl implements SysMenuService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());  
	@Autowired
	private SysMenuDao sysMenuDao;

	@Override
	//@Cacheable(value = "system", key = "'menus'")
	public List<SysMenu> queryMenus() {
		logger.debug("查询所有菜单");
		return sysMenuDao.selectAll();
	}
	@Override
	public List<Map<String, Object>> queryWithParams(QueryRq query){
		return sysMenuDao.selectWithParams(query.getRq());
	}
	@Override
	public boolean addOrEdit(SysMenu menu) {
		int res = 0;
		//if(menu.getMenuId() != null)//修改
			//res = sysMenuDao.updateByPrimaryKeySelective(menu);
		//else//新增
			//res = sysMenuDao.insertSelective(menu);
		res = sysMenuDao.insertOrUpdateSelective(menu);//自定义动态sql生成
		//insert 为 1  update 为2
		return res == 1 || res == 2;
	}
	@Override
	public List<Map<String, Object>> queryBySql(){
		String sql = "select menu_id, menu_name from sys_menu";
		return sysMenuDao.selectBySql(sql);
	}
}
