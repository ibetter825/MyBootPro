package com.mypro.configure.security.authentication;

import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Service;

@Service
public class MySecurityInterceptor extends AbstractSecurityInterceptor implements Filter {
	private static final Logger logger = LoggerFactory.getLogger(MySecurityInterceptor.class);
	@Autowired  
    private MyInvocationSecurityMetadataSourceService securityMetadataSource;  
      
    @Autowired  
    private MyAccessDecisionManager accessDecisionManager;
      
    @Autowired  
    private AuthenticationManager authenticationManager;
      
    @PostConstruct  
    public void init(){//在这里将ss默认的用于权限验证的类替换掉
        super.setAuthenticationManager(authenticationManager);
        super.setAccessDecisionManager(accessDecisionManager);
    }  
      
    public void doFilter( ServletRequest request, ServletResponse response, FilterChain chain)
    throws IOException, ServletException{
        FilterInvocation fi = new FilterInvocation( request, response, chain );  
        invoke(fi);  
    }
  
    public Class<? extends Object> getSecureObjectClass(){
        return FilterInvocation.class;  
    }  
      
    public void invoke( FilterInvocation fi ) throws IOException, ServletException{
        InterceptorStatusToken token = super.beforeInvocation(fi);
        try{
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        }finally{
            super.afterInvocation(token, null);
        }
    }

	@Override  
    public SecurityMetadataSource obtainSecurityMetadataSource(){
        return this.securityMetadataSource;
    }
      
    public void destroy(){
        logger.debug("SpringSecurity: MySecurityInterceptor destroy");
    }
    
    public void init( FilterConfig filterconfig ) throws ServletException{  
    	logger.debug("SpringSecurity: MySecurityInterceptor init");
    }
}
