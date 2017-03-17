package com.mypro.configure;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.mypro.web.interceptor.admin.AuthSecurityInterceptor;

/**
 * 配置spring mvc的拦截器WebMvcConfigurerAdapter 
 * @author user
 */
@Configuration
public class IntercepterConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new AuthSecurityInterceptor()).addPathPatterns("/tag/**");
	}
}
