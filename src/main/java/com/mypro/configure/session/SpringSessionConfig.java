package com.mypro.configure.session;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * Spring Session 配置
 * 这样简单配置后Session会自动存入Redis
 * @author user
 *
 */
@Configuration
@EnableRedisHttpSession
public class SpringSessionConfig {
	@Value("${spring.redis.host}")  
    private String host;
      
    @Value("${spring.redis.port}")
    private Integer port;
    
	@Bean  
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
        connectionFactory.setHostName(host);
        connectionFactory.setPort(port);
        return connectionFactory;
    }
	
    @Bean  
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
}
