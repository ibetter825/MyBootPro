package com.mypro.service.admin.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mypro.bean.entity.admin.SysMenu;
import com.mypro.bean.rq.QueryRq;
import com.mypro.configure.security.customer.MyUserDetails;
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
	public List<SysMenu> queryMenus(MyUserDetails user) {
		if(user.getIsSuper() == 1)//超级管理员
			return sysMenuDao.selectAll();
		else{
			StringBuffer buffer = new StringBuffer();
			List<Map<String, Object>> opts = user.getOpts();
			Map<String, String> rq = Maps.newHashMap();
			String menuIds = "";
			if(opts.size() != 0){
				for (Map<String, Object> opt : opts) {
					if(buffer.indexOf(opt.get("menu_id")+",") == -1)
						buffer.append(opt.get("menu_id")+",");
				}
				menuIds = buffer.substring(0, buffer.length() - 1).toString();
			}
			rq.put("menuIds", "'"+menuIds+"'");
			return sysMenuDao.selectWithMenuIds(rq);
		}
	}
	@Override
	public List<Map<String, Object>> queryWithParams(QueryRq query){
		return sysMenuDao.selectWithParams(query.getRq());
	}
	@Override
	public boolean addOrEdit(SysMenu menu) {
		int res = sysMenuDao.insertOrUpdateSelective(menu);//自定义动态sql生成
		return res == 1 || res == 2;
	}
	@Override
	public List<Map<String, Object>> queryBySql(){
		String sql = "select menu_id, menu_name from sys_menu";
		return sysMenuDao.selectBySql(sql);
	}
	@Override
	public List<Integer> batchRemove(SysMenu[] menus) {
		List<Integer> errs = Lists.newArrayList();
		for (SysMenu menu : menus) {
			if(sysMenuDao.updateByPrimaryKeySelective(menu) != 1)
				errs.add(menu.getMenuId());
		}
		return errs;
	}
}
