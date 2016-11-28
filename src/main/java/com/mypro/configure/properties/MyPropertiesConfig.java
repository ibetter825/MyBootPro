package com.mypro.configure.properties;

import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * 我的属性配置
 * 读取application.yml文件
 * @author user
 *
 */
@Component
public class MyPropertiesConfig implements EnvironmentAware {

	private static RelaxedPropertyResolver properties;
	@Override
	public void setEnvironment(Environment environment) {
		properties = new RelaxedPropertyResolver(environment, "properties.");
	}
	public static String getProperty(String key){
		return properties.getProperty(key);
	}
}
