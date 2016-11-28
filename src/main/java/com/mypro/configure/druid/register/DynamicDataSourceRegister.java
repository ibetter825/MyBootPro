package com.mypro.configure.druid.register;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.bind.RelaxedDataBinder;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import com.alibaba.druid.pool.DruidDataSource;
import com.mypro.configure.datasource.DynamicDataSource;
import com.mypro.context.DynamicDataSourceContextHolder;

/**
 * 动态数据源注册
 * 启动动态数据源请在启动类中（如SpringBootSampleApplication）
 * 添加 @Import(DynamicDataSourceRegister.class)
 * @author user
 *
 */
public class DynamicDataSourceRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware {
	private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceRegister.class);
	
	private ConversionService conversionService = new DefaultConversionService();
	private PropertyValues dataSourcePropertyValues;
    // 数据源
    private DataSource defaultDataSource;
    private Map<String, DataSource> customDataSources = new HashMap<String, DataSource>();
    private RelaxedPropertyResolver propertyResolver;
    
	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
		// 将主数据源添加到更多数据源中
		targetDataSources.put("dataSource", defaultDataSource);
		DynamicDataSourceContextHolder.dataSourceIds.add("dataSource");
		//添加更多数据源
		targetDataSources.putAll(customDataSources);
		for (String key : customDataSources.keySet()) {
            DynamicDataSourceContextHolder.dataSourceIds.add(key);
        }
		
		// 创建DynamicDataSource
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(DynamicDataSource.class);
        beanDefinition.setSynthetic(true);
        MutablePropertyValues mpv = beanDefinition.getPropertyValues();
        mpv.addPropertyValue("defaultTargetDataSource", defaultDataSource);
        mpv.addPropertyValue("targetDataSources", targetDataSources);
        registry.registerBeanDefinition("dataSource", beanDefinition);
        
        logger.info("动态数据源注册");
	}
	/**
	 * 创建数据源
	 * @param dsMap
	 * @return
	 */
	public DataSource buildDataSource(Map<String, Object> dsMap) {
            String driverClassName = dsMap.get("driverClassName").toString();
            String url = dsMap.get("url").toString();
            String username = dsMap.get("username").toString();
            String password = dsMap.get("password").toString();
            
            DruidDataSource datasource = new DruidDataSource();  
            datasource.setUrl(url);  
            datasource.setUsername(username);  
            datasource.setPassword(password);  
            datasource.setDriverClassName(driverClassName);  
            
            //初始化大小，最小，最大
            datasource.setInitialSize(Integer.parseInt(propertyResolver.getProperty("initialSize")));  
            datasource.setMinIdle(Integer.parseInt(propertyResolver.getProperty("minIdle")));  
            datasource.setMaxActive(Integer.parseInt(propertyResolver.getProperty("maxActive")));
            //配置获取连接等待超时的时间
            datasource.setMaxWait(Long.parseLong(propertyResolver.getProperty("maxWait"))); 
            //配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
            datasource.setTimeBetweenEvictionRunsMillis(Long.parseLong(propertyResolver.getProperty("timeBetweenEvictionRunsMillis")));  
            //配置一个连接在池中最小生存的时间，单位是毫秒
            datasource.setMinEvictableIdleTimeMillis(Long.parseLong(propertyResolver.getProperty("minEvictableIdleTimeMillis")));  
            datasource.setValidationQuery(propertyResolver.getProperty("validationQuery"));  
            datasource.setTestWhileIdle(Boolean.parseBoolean(propertyResolver.getProperty("testWhileIdle")));  
            datasource.setTestOnBorrow(Boolean.parseBoolean(propertyResolver.getProperty("testOnBorrow")));  
            datasource.setTestOnReturn(Boolean.parseBoolean(propertyResolver.getProperty("testOnReturn")));  
            //打开PSCache，并且指定每个连接上PSCache的大小
            datasource.setPoolPreparedStatements(Boolean.parseBoolean(propertyResolver.getProperty("poolPreparedStatements")));  
            datasource.setMaxPoolPreparedStatementPerConnectionSize(Integer.parseInt(propertyResolver.getProperty("maxPoolPreparedStatementPerConnectionSize")));  
            try {  
                datasource.setFilters(propertyResolver.getProperty("filters"));  //配置监控统计拦截的filters，去掉后监控界面sql无法统计，'wall'用于防火墙
            } catch (SQLException e) {  
                e.printStackTrace();
            }  
            datasource.setConnectionProperties(propertyResolver.getProperty("connectionProperties"));
            return datasource;
    }
	 /**
     * 初始化主数据源
     *
     * @author SHANHY
     * @create 2016年1月24日
     */
    private void initDefaultDataSource(Environment env) {
        // 读取主数据源
        //RelaxedPropertyResolver propertyResolver = new RelaxedPropertyResolver(env, "spring.datasource.");
        Map<String, Object> dsMap = new HashMap<String, Object>();
        dsMap.put("driverClassName", propertyResolver.getProperty("driverClassName"));
        dsMap.put("url", propertyResolver.getProperty("url"));
        dsMap.put("username", propertyResolver.getProperty("username"));
        dsMap.put("password", propertyResolver.getProperty("password"));
        defaultDataSource = buildDataSource(dsMap);
        dataBinder(defaultDataSource, env);
    }
    /**
     * 为DataSource绑定更多数据
     *
     * @param dataSource
     * @param env
     * @author SHANHY
     * @create  2016年1月25日
     */
    private void dataBinder(DataSource dataSource, Environment env){
        RelaxedDataBinder dataBinder = new RelaxedDataBinder(dataSource);
        dataBinder.setConversionService(conversionService);
        dataBinder.setIgnoreNestedProperties(false);//false
        dataBinder.setIgnoreInvalidFields(false);//false
        dataBinder.setIgnoreUnknownFields(true);//true
        if(dataSourcePropertyValues == null){
            Map<String, Object> rpr = new RelaxedPropertyResolver(env, "spring.datasource").getSubProperties(".");
            Map<String, Object> values = new HashMap<String, Object>(rpr);
            // 排除已经设置的属性
            values.remove("driverClassName");
            values.remove("url");
            values.remove("username");
            values.remove("password");
            dataSourcePropertyValues = new MutablePropertyValues(values);
        }
        dataBinder.bind(dataSourcePropertyValues);
    }
    /**
     * 初始化更多数据源
     *
     * @author SHANHY
     * @create 2016年1月24日
     */
    private void initCustomDataSources(Environment env) {
        // 读取配置文件获取更多数据源，也可以通过defaultDataSource读取数据库获取更多数据源
        //RelaxedPropertyResolver propertyResolver = new RelaxedPropertyResolver(env, "spring.datasource.");
        String dsPrefixs = propertyResolver.getProperty("names");
        if(dsPrefixs == null)
        	return;
        for (String dsPrefix : dsPrefixs.split(",")) {// 多个数据源
            Map<String, Object> dsMap = propertyResolver.getSubProperties(dsPrefix + ".");
            DataSource ds = buildDataSource(dsMap);
            customDataSources.put(dsPrefix, ds);
            dataBinder(ds, env);
        }
    }
	@Override
	public void setEnvironment(Environment env) {
		propertyResolver = new RelaxedPropertyResolver(env, "spring.datasource.");
		initDefaultDataSource(env);
        initCustomDataSources(env);
	}
}
