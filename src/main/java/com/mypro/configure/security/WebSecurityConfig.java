package com.mypro.configure.security;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import com.mypro.bean.constant.SecurityConstant;
import com.mypro.configure.security.customer.MyAuthenticationProvider;
import com.mypro.configure.security.customer.MyPersistentTokenRepository;
import com.mypro.configure.security.handler.MyLogOutHandler;
import com.mypro.configure.security.handler.MyLoginFailureHandler;
import com.mypro.configure.security.handler.MyLoginSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)//开启对方法的验证注解
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	//配置使用自定义的AuthenticationDetailsSource，用于新增登录表单字段
	@Autowired
    private AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> authenticationDetailsSource;
	@Autowired
	private MyAuthenticationProvider provider;//自定义验证
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private MyPersistentTokenRepository myPersistentTokenRepository;

	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().sameOrigin()//设置页面可以被同域名下的iframe嵌套iframe嵌套
        	.and().authorizeRequests()
                .antMatchers("/", "/static/**", "/tag/*").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
            	//.failureUrl("/admin/login?error")
                .loginPage("/admin/login")
                //.defaultSuccessUrl("/admin/main")
                .successHandler(new MyLoginSuccessHandler())//登录成功后处理
                .failureHandler(new MyLoginFailureHandler())//登录失败后处理
                .authenticationDetailsSource(authenticationDetailsSource)
                .permitAll()
                .and()
            .logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/admin/logout"))
            //注销失败跳转到登录页面
            .logoutSuccessUrl("/admin/login")
            .addLogoutHandler(new MyLogOutHandler())
                .permitAll()
                .and()
                .rememberMe()
                .tokenRepository(myPersistentTokenRepository)//用于持久化cookie到数据库
                .userDetailsService(userDetailsService)//由于使用了自定义的UserDetailsService 需要在此覆盖默认的UserDetailsService
                //.key("remember-me-key")
                .rememberMeCookieName(SecurityConstant.REMEMBER_ME_COOKIE_NAME)
                .tokenValiditySeconds(SecurityConstant.COOKIE_VALIDITY_SECONDS);//cookie有效期为14天
        
        //关闭csrf 防止循环定向
        http.csrf().disable();
    }

	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    	//将验证过程交给自定义验证工具
        auth.authenticationProvider(provider);
    }
}
