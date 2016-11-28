package com.mypro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.mypro.configure.druid.register.DynamicDataSourceRegister;

/**
 * Spring-boot入口
 * @author user
 *
 */
@Configuration
@EnableAutoConfiguration
//@EnableScheduling  //注解的作用是发现注解@Scheduled的任务并后台执行。
@ServletComponentScan
@ComponentScan
@Import({DynamicDataSourceRegister.class})// 注册动态多数据源
public class Application extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
