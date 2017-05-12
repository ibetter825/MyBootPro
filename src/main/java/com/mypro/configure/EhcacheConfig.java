package com.mypro.configure;

import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

/**
 * Ehcache配置
 * 由于采用了redis所以放弃使用ehcache
 * @author user
 *
 */
//@Configuration
public class EhcacheConfig {
	
	@Bean
	public EhCacheCacheManager ehCacheCacheManager(EhCacheManagerFactoryBean bean){
		return new EhCacheCacheManager(bean.getObject());
	}
	
	@Bean
	public EhCacheManagerFactoryBean ehCacheManagerFactoryBean(){
        EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean ();
        cacheManagerFactoryBean.setConfigLocation (new ClassPathResource("ehcache.xml"));
        cacheManagerFactoryBean.setShared(true);
        return cacheManagerFactoryBean;
      }
}
