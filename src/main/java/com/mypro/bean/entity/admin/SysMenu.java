package com.mypro.bean.entity.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.mypro.bean.entity.BaseEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * 系统菜单
 * @author user
 *
 */
@Getter @Setter
public class SysMenu extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer menuId;
	private String menuName;
	private String menuUrl;
	private String menuDesc;
	private Integer menuPid;
	private Short menuState;
	private Short menuLevel;
	private Integer menuSeq;
	private String menuIcon;
	
	/**
	 * 递归获取菜单树
	 * @param menus
	 * @param menuPid
	 * @return
	 */
	public static List<Map<String, Object>> getMenuTree(List<SysMenu> menus, Integer menuPid){
		if(menus == null)
			return null;
		List<Map<String, Object>> tree = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;
		for (SysMenu m : menus) {
			if(menuPid == null){
				if(!m.getMenuPid().equals(0))//这是一级菜单
					continue;
			}else{
				if(!m.getMenuPid().equals(menuPid))
					continue;
			}
			map = new HashMap<String, Object>();
			map.put("id", m.getMenuId());
	    	map.put("title", m.getMenuName());
	    	map.put("icon", m.getMenuIcon());
	    	map.put("level", m.getMenuLevel());
	    	map.put("url", m.getMenuUrl());
        	map.put("children", getMenuTree(menus, m.getMenuId()));
        	tree.add(map);
		}
		return tree;
	}
}
