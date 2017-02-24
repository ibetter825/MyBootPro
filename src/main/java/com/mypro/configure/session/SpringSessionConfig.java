package com.mypro.configure.session;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * Spring Session 配置
 * 这样简单配置后Session会自动存入Redis
 * Redis集群这样配置也可
 * maxInactiveIntervalInSeconds: session在redis中的超时时间默认30分钟
 * @author user
 *
 */
@Configuration
@EnableRedisHttpSession//(maxInactiveIntervalInSeconds = 60)
public class SpringSessionConfig {
	
    @Bean  
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
}
