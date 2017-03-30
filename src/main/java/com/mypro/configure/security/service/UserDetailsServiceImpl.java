package com.mypro.configure.security.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mypro.bean.constant.SecurityConstant;
import com.mypro.bean.entity.admin.SysRole;
import com.mypro.bean.entity.admin.SysUser;
import com.mypro.configure.security.customer.MyUserDetails;
import com.mypro.service.admin.SysUserService;
/**
 * security 自定义登录验证
 * @author user
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private SysUserService sysUserService;
	
	@Override
	public UserDetails loadUserByUsername(String user_name) throws UsernameNotFoundException {
		SysUser user;
        try {
            user = sysUserService.queryUserByName(user_name);
        } catch (Exception e) {
            throw new UsernameNotFoundException(SecurityConstant.USER_NAME_NOT_FOUND_MSG);
        }
        if(user == null){
            throw new UsernameNotFoundException(SecurityConstant.USER_NAME_NOT_FOUND_MSG);
        } else {
            try {
                List<SysRole> roles = new ArrayList<SysRole>();
                SysRole role = new SysRole();
                role.setRoleName("超级管理员");
                role.setRoleId(1);
                role.setRoleState((short) 1);
                roles.add(role);
                return new MyUserDetails(user, roles);
            } catch (Exception e) {
                throw new UsernameNotFoundException("user role select fail");
            }
        }
	}

}
