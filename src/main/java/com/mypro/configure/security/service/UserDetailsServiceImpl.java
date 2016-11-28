package com.mypro.configure.security.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mypro.bean.constant.SecurityConstant;
import com.mypro.bean.entity.SysRole;
import com.mypro.bean.entity.SysUser;
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
            throw new UsernameNotFoundException(SecurityConstant.USER_NAME_NOT_FOUND);
        }
        if(user == null){
            throw new UsernameNotFoundException(SecurityConstant.USER_NAME_NOT_FOUND);
        } else {
            try {
                List<SysRole> roles = new ArrayList<SysRole>();
                SysRole role = new SysRole();
                role.setRole_name("超级管理员");
                role.setRole_no("super");
                role.setRole_state((short) 1);
                roles.add(role);
                return new MyUserDetails(user, roles);
            } catch (Exception e) {
                throw new UsernameNotFoundException("user role select fail");
            }
        }
	}

}
