package com.mypro.service.admin;

import com.mypro.bean.entity.admin.SysUser;

/**
 * 系统用户 service接口
 * @author user
 *
 */
public interface SysUserService {
	/**
	 * 通过用户登录名查询用户
	 * @param user_name
	 * @return
	 */
	public SysUser queryUserByName(String user_name);
}
