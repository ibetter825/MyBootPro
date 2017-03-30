package com.mypro.bean.entity.admin;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer roleId;
	private String roleName;
	private Integer rolePid;
	private String roleDesc;
	private Short roleState;
	private Short isSuper;
	private Integer groupId;
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public Integer getRolePid() {
		return rolePid;
	}
	public void setRolePid(Integer rolePid) {
		this.rolePid = rolePid;
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
	public Short getIsSuper() {
		return isSuper;
	}
	public void setIsSuper(Short isSuper) {
		this.isSuper = isSuper;
	}
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
}
