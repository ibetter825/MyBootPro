package com.mypro.bean.constant;
/**
 * spring security 常量使用
 * @author user
 *
 */
public class CacheConstant {
	/**
	 * 默认缓存超时时间,s 30分钟
	 */
	public final static long CACHE_DEFAULT_EXPIRATION = 1800;
	/**
	 * 自定义缓存时间设置,多个用;号分开
	 */
	public final static String CACHE_CUSTOM_EXPIRES = "demo:0";
}
