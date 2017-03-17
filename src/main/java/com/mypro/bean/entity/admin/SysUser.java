package com.mypro.bean.entity.admin;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.mypro.bean.entity.BaseEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * 系统用户
 * @author user
 *
 */
@Getter @Setter
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
}
