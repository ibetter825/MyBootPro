package com.mypro.configure.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.mypro.bean.constant.SecurityConstant;
import com.mypro.bean.entity.admin.SysUser;
import com.mypro.configure.security.customer.MyUserDetails;
import com.mypro.configure.security.exception.SecurityCommonException;
import com.mypro.dao.admin.SysOptDao;
import com.mypro.dao.admin.SysUserDao;
/**
 * security 自定义登录验证
 * @author user
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private SysUserDao userDao;
	@Autowired
	private SysOptDao optDao;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		SysUser user;
        try {
        	user = new SysUser();
    		user.setUserName(userName);
            user = userDao.selectOne(user);
        } catch (Exception e) {
            throw new UsernameNotFoundException(SecurityConstant.USER_NAME_NOT_FOUND_MSG);
        }
        if(user == null){
            throw new UsernameNotFoundException(SecurityConstant.USER_NAME_NOT_FOUND_MSG);
        } else {
            try {
                /*List<SysRole> roles = new ArrayList<SysRole>();
                SysRole role = new SysRole();
                role.setRoleName("超级管理员");
                role.setRoleId(1);
                role.setRoleState((short) 1);
                roles.add(role);
                return new MyUserDetails(user, roles);*/
            	return new MyUserDetails(user, optDao.selectOptsByUser(user.getUserId()));
            } catch (Exception e) {
                throw new SecurityCommonException(SecurityConstant.USER_RIGHT_SELECT_FAIL);
            }
        }
	}

}
