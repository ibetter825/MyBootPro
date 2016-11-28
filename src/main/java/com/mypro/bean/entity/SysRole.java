package com.mypro.bean.entity;

import javax.persistence.Id;

/**
 * 系统用户角色
 * @author user
 *
 */
public class SysRole extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	@Id
	private String role_no;
	private String role_name;
	private String role_desc;
	private Short role_state;
	public String getRole_no() {
		return role_no;
	}
	public void setRole_no(String role_no) {
		this.role_no = role_no;
	}
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
	public String getRole_desc() {
		return role_desc;
	}
	public void setRole_desc(String role_desc) {
		this.role_desc = role_desc;
	}
	public Short getRole_state() {
		return role_state;
	}
	public void setRole_state(Short role_state) {
		this.role_state = role_state;
	}
	
}
