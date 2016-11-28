package com.mypro.bean.entity;

import javax.persistence.Id;

/**
 * 系统用户
 * @author user
 *
 */
public class SysUser extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	@Id
	private String user_no;
	private String user_pwd;
	private String user_salt;
	private String user_name;
	private Short user_state;
	
	public SysUser() {}
	
	/**
	 * 在MyUserDetails中调用了此构造器
	 * @param sysUser
	 */
	public SysUser(SysUser sysUser) {
		this.user_name = sysUser.getUser_name();
		this.user_pwd = sysUser.getUser_pwd();
		this.user_salt = sysUser.getUser_salt();
	}

	public String getUser_no() {
		return user_no;
	}

	public void setUser_no(String user_no) {
		this.user_no = user_no;
	}

	public String getUser_pwd() {
		return user_pwd;
	}

	public void setUser_pwd(String user_pwd) {
		this.user_pwd = user_pwd;
	}

	public String getUser_salt() {
		return user_salt;
	}

	public void setUser_salt(String user_salt) {
		this.user_salt = user_salt;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public Short getUser_state() {
		return user_state;
	}

	public void setUser_state(Short user_state) {
		this.user_state = user_state;
	}

}
