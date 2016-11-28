package com.mypro.configure.session;

import org.springframework.context.annotation.Configuration;
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

}
