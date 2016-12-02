package com.mypro.bean.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 系统用户
 * @author user
 *
 */
public class SysUser extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String userId;
	private String userPwd;
	private String userSalt;
	private String userName;
	private Short userState;
	private Short isSuper;
	
	public SysUser() {}
	
	/**
	 * 在MyUserDetails中调用了此构造器
	 * @param sysUser
	 */
	public SysUser(SysUser sysUser) {
		this.userName = sysUser.getUserName();
		this.userPwd = sysUser.getUserPwd();
		this.userSalt = sysUser.getUserSalt();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getUserSalt() {
		return userSalt;
	}

	public void setUserSalt(String userSalt) {
		this.userSalt = userSalt;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Short getUserState() {
		return userState;
	}

	public void setUserState(Short userState) {
		this.userState = userState;
	}

	public Short getIsSuper() {
		return isSuper;
	}

	public void setIsSuper(Short isSuper) {
		this.isSuper = isSuper;
	}

}
