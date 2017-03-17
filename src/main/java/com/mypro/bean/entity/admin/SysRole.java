package com.mypro.bean.entity.admin;

import javax.persistence.Id;

import com.mypro.bean.entity.BaseEntity;

/**
 * 系统用户角色
 * @author user
 *
 */
public class SysRole extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	@Id
	private String roleNo;
	private String roleName;
	private String rolePno;
	private String roleDesc;
	private Short roleState;
	public String getRoleNo() {
		return roleNo;
	}
	public void setRoleNo(String roleNo) {
		this.roleNo = roleNo;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRolePno() {
		return rolePno;
	}
	public void setRolePno(String rolePno) {
		this.rolePno = rolePno;
	}
	public String getRoleDesc() {
		return roleDesc;
	}
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
	public Short getRoleState() {
		return roleState;
	}
	public void setRoleState(Short roleState) {
		this.roleState = roleState;
	}
	
}
