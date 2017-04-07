package com.mypro.configure.security.authentication;

import java.util.Collection;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Service;

import com.mypro.bean.constant.SecurityConstant;
import com.mypro.bean.constant.WebConstant;
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
		if(configAttributes == null) return;
		MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
		if(userDetails.getIsSuper() == 1) return;//超级管理员拥有所有权限不需要再验证权限
		FilterInvocation filterInvocation = (FilterInvocation) object;
		String reqUri = filterInvocation.getHttpRequest().getRequestURI();
		if(!reqUri.startsWith(WebConstant.ADMIN_REQUEST_ROOT_PATH)) return;//不拦截/admin/**以外的路径
		
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority auth : authorities)
			if(auth.getAuthority().contains(reqUri)) return; //如果包含该权限则通过验证
        throw new AccessDeniedException(SecurityConstant.USER_RIGHT_VALI_FAIL); 
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
