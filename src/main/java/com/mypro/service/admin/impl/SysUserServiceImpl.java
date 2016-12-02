package com.mypro.service.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mypro.bean.entity.SysUser;
import com.mypro.dao.admin.SysUserDao;
import com.mypro.service.admin.SysUserService;

@Service
public class SysUserServiceImpl implements SysUserService {

	@Autowired
	private SysUserDao sysUserDao;
	
	@Override
	public SysUser queryUserByName(String user_name) {
		SysUser user = new SysUser();
		user.setUserName(user_name);
		user = sysUserDao.selectOne(user);
		return user;
	}
	
}
