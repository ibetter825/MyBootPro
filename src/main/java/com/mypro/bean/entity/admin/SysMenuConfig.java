package com.mypro.bean.entity.admin;

import javax.persistence.Id;

import com.mypro.bean.entity.BaseEntity;

/**
 * 系统菜单
 * @author user
 *
 */
public class SysMenuConfig extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer menuId;
	private String cfgSearch;
	private String cfgGrid;
	private String cfgObject;
	private String cfgGlobal;
	
	public Integer getMenuId() {
		return menuId;
	}
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
	public String getCfgSearch() {
		return cfgSearch;
	}
	public void setCfgSearch(String cfgSearch) {
		this.cfgSearch = cfgSearch;
	}
	public String getCfgGrid() {
		return cfgGrid;
	}
	public void setCfgGrid(String cfgGrid) {
		this.cfgGrid = cfgGrid;
	}
	public String getCfgObject() {
		return cfgObject;
	}
	public void setCfgObject(String cfgObject) {
		this.cfgObject = cfgObject;
	}
	public String getCfgGlobal() {
		return cfgGlobal;
	}
	public void setCfgGlobal(String cfgGlobal) {
		this.cfgGlobal = cfgGlobal;
	}
}
