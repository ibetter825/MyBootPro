package com.mypro.configure.redis;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mypro.configure.properties.RedisPropertiesConfig;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis集群使用的配置
 * @author user
 *
 */
@Configuration
@EnableCaching
public class RedisClusterConfig {
	@Autowired
	private RedisPropertiesConfig properties;
	// Jedis连接池
	@Bean
	public JedisPool redisPoolFactory() {
 
		// JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port,
		// timeout, password);
		return new JedisPool(jedisPoolConfig(), properties.getHost(), properties.getPort(), properties.getTimeout());
	}
	
	// JedisCluster
	@Bean
	public JedisCluster JedisClusterFactory() {
		Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
		JedisCluster jedisCluster = new JedisCluster(jedisClusterNodes, jedisPoolConfig());
		return jedisCluster;
	}
	
	@Bean
	public KeyGenerator wiselyKeyGenerator() {
		return new KeyGenerator() {
			@Override
			public Object generate(Object target, Method method, Object... params) {
				StringBuilder sb = new StringBuilder();
				sb.append(target.getClass().getName());
				sb.append(method.getName());
				for (Object obj : params) {
					sb.append(obj.toString());
				}
				return sb.toString();
			}
		};
	}
	
	@Bean
    public JedisConnectionFactory connectionFactory() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisClusterConfiguration(),jedisPoolConfig());
        
        jedisConnectionFactory.setUsePool(true);
        jedisConnectionFactory.setTimeout(properties.getTimeout());
        
        return jedisConnectionFactory; 
    }
	
	@Bean
	public CacheManager cacheManager(RedisTemplate<?, ?> redisTemplate) {
		RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
		// Number of seconds before expiration. Defaults to unlimited (0)
		cacheManager.setDefaultExpiration(10); // 设置key-value超时时间
		return cacheManager;
	}
 
	@Bean
	public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
		StringRedisTemplate template = new StringRedisTemplate(factory);
		setSerializer(template); // 设置序列化工具，这样ReportBean不需要实现Serializable接口
		template.afterPropertiesSet();
		return template;
	}
 
	private void setSerializer(StringRedisTemplate template) {
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(
				Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);
		template.setValueSerializer(jackson2JsonRedisSerializer);
		//template.setKeySerializer(jackson2JsonRedisSerializer);
		//template.setHashKeySerializer(jackson2JsonRedisSerializer);
	}
	
	/**
     * redis集群配置
     * 
     * 配置redis集群的结点及其它一些属性
     * 
     * @return
     */
    private RedisClusterConfiguration redisClusterConfiguration(){
        RedisClusterConfiguration redisClusterConfig = new RedisClusterConfiguration();

        redisClusterConfig.setClusterNodes(getClusterNodes());
        
        redisClusterConfig.setMaxRedirects(3);
        return redisClusterConfig;
        
    }
    
    /**
     * JedisPoolConfig 配置
     * 
     * 配置JedisPoolConfig的各项属性
     * 
     * @return
     */
    private JedisPoolConfig jedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig= new JedisPoolConfig();
        //连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
        jedisPoolConfig.setBlockWhenExhausted(properties.isBlockWhenExhausted());
        
        //是否启用pool的jmx管理功能, 默认true
        jedisPoolConfig.setJmxEnabled(properties.isJmxEnabled());
        
        //默认就好
        //jedisPoolConfig.setJmxNamePrefix("pool");
        
        //jedis调用returnObject方法时，是否进行有效检查
        jedisPoolConfig.setTestOnReturn(properties.isTestOnReturn());
        
        //是否启用后进先出, 默认true
        jedisPoolConfig.setLifo(properties.isLifo());
        
        //最大空闲连接数, 默认8个
        jedisPoolConfig.setMaxIdle(properties.getMaxIdle());
        
        //最大连接数, 默认8个
        jedisPoolConfig.setMaxTotal(properties.getMaxTotal());
        
        //获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
        jedisPoolConfig.setMaxWaitMillis(properties.getMaxWaitMillis());
        
        //逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
        jedisPoolConfig.setMinEvictableIdleTimeMillis(properties.getMinEvictableIdleTimeMillis());
        
        //最小空闲连接数, 默认0
        jedisPoolConfig.setMinIdle(properties.getMinIdle());
        
        //每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
        jedisPoolConfig.setNumTestsPerEvictionRun(properties.getNumTestsPerEvictionRun());
        
        //对象空闲多久后逐出, 当空闲时间>该值 且 空闲连接>最大空闲数 时直接逐出,不再根据MinEvictableIdleTimeMillis判断  (默认逐出策略)   
        jedisPoolConfig.setSoftMinEvictableIdleTimeMillis(properties.getSoftMinEvictableIdleTimeMillis());
        
        //在获取连接的时候检查有效性, 默认false
        jedisPoolConfig.setTestOnBorrow(properties.isTestOnBorrow());
        
        //在空闲时检查有效性, 默认false
        jedisPoolConfig.setTestWhileIdle(properties.isTestWhileIdle());
        
        //逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(properties.getTimeBetweenEvictionRunsMillis());
        
        return jedisPoolConfig;
    }
    
    /**
     * redis集群节点IP和端口的添加
     * 
     * 节点：RedisNode redisNode = new RedisNode("127.0.0.1",6379);
     * 
     * @return
     */
    private Set<RedisNode> getClusterNodes(){
        // 添加redis集群的节点
        Set<RedisNode> clusterNodes = new HashSet<RedisNode>();
        String[] nodes = properties.getClusterNodes().split(",");
        String[] node = new String[2];
        for (String str : nodes) {
        	node = str.split(":");
        	clusterNodes.add(new RedisNode(node[0], Integer.valueOf(node[1])));
		}
        return clusterNodes;
    }
}
