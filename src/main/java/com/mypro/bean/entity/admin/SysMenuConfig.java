package com.mypro.bean.entity.admin;

import javax.persistence.Id;

import com.mypro.bean.entity.BaseEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * 系统菜单
 * @author user
 *
 */
@Getter
@Setter
public class SysMenuConfig extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer menuId;
	private String cfgSearch;
	private String cfgGrid;
	private String cfgObject;
	private String cfgGlobal;
}
