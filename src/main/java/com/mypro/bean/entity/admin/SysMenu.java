package com.mypro.bean.entity.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import com.mypro.bean.entity.BaseEntity;


/**
 * 系统菜单
 * @author user
 *
 */
public class SysMenu extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer menuId;
	private String menuRoute;
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
	    	map.put("name", m.getMenuName());
	    	map.put("clzz", m.getMenuIcon());
	    	map.put("level", m.getMenuLevel());
	    	map.put("uri", m.getMenuUrl());
        	map.put("children", getMenuTree(menus, m.getMenuId()));
        	tree.add(map);
		}
		return tree;
	}

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public String getMenuRoute() {
		return menuRoute;
	}

	public void setMenuRoute(String menuRoute) {
		this.menuRoute = menuRoute;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public String getMenuDesc() {
		return menuDesc;
	}

	public void setMenuDesc(String menuDesc) {
		this.menuDesc = menuDesc;
	}

	public Integer getMenuPid() {
		return menuPid;
	}

	public void setMenuPid(Integer menuPid) {
		this.menuPid = menuPid;
	}

	public Short getMenuState() {
		return menuState;
	}

	public void setMenuState(Short menuState) {
		this.menuState = menuState;
	}

	public Short getMenuLevel() {
		return menuLevel;
	}

	public void setMenuLevel(Short menuLevel) {
		this.menuLevel = menuLevel;
	}

	public Integer getMenuSeq() {
		return menuSeq;
	}

	public void setMenuSeq(Integer menuSeq) {
		this.menuSeq = menuSeq;
	}

	public String getMenuIcon() {
		return menuIcon;
	}

	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}
}
