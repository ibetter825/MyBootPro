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
	
	public Integer getMenuId() {
		return menuId;
	}
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
}
