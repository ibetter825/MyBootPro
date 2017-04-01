package com.mypro.configure.security.Authentication;

import java.util.Collection;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Service;

import com.mypro.configure.security.customer.MyUserDetails;

@Service
public class MyAccessDecisionManager implements AccessDecisionManager {

	/** 
     * 检查用户是否够权限访问资源 
     * authentication 是从spring的全局缓存SecurityContextHolder中拿到的，里面是用户的权限信息 
     * object 是url 
     * configAttributes 所需的权限 
     * @see org.springframework.security.access.AccessDecisionManager#decide(org.springframework.security.core.Authentication, java.lang.Object, java.util.Collection) 
     */  
	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		if(configAttributes == null)return;  
		MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
		if(userDetails.getIsSuper() == 1)return;//超级管理员拥有所有权限不需要再判断下面的权限
		FilterInvocation filterInvocation = (FilterInvocation) object;
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority auth : authorities) {
			if(auth.getAuthority().contains(filterInvocation.getHttpRequest().getRequestURI()))
				return;
		}
        throw new AccessDeniedException("权限不足"); 
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

}
