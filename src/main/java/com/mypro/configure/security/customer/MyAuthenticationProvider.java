package com.mypro.configure.security.customer;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.mypro.bean.constant.SecurityConstant;
import com.mypro.common.utils.Md5Util;
import com.mypro.configure.security.exception.WrongCaptchaException;
import com.mypro.configure.security.service.UserDetailsServiceImpl;

@Component
public class MyAuthenticationProvider implements AuthenticationProvider {

	@Autowired
    private UserDetailsServiceImpl userService;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// 这里通过authentication.getDetails() 获取详细信息,新增的表单字段
		MyWebAuthenticationDetails details = (MyWebAuthenticationDetails) authentication.getDetails();
		String captcha = details.getCaptcha();//验证码
		if(!"captcha".equals(captcha))
			throw new WrongCaptchaException(SecurityConstant.WRONG_CAPTCHA_MSG);
		
	 	String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        MyUserDetails user = (MyUserDetails) userService.loadUserByUsername(username);
        //在此方法中加入登录判断，用户名， 密码，验证码等等
        
        if(user == null)
            throw new UsernameNotFoundException(SecurityConstant.USER_NAME_NOT_FOUND_MSG);

        String usersalt = user.getUserSalt();
        //加密过程在这里体现
        if (!Md5Util.md5(password+usersalt).equals(user.getPassword()))
            throw new BadCredentialsException(SecurityConstant.WRONG_PASSWORD_MSG);

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        return new UsernamePasswordAuthenticationToken(user, password, authorities);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}

}
