package com.mypro.bean.constant;
/**
 * spring security 常量使用
 * @author user
 *
 */
public class SecurityConstant {
	/**
	 * 记住我默认cookie名
	 */
	public final static String REMEMBER_ME_COOKIE_NAME = "YTPROAUTHID";
	/**
	 * cookie的有效期
	 */
	public final static int COOKIE_VALIDITY_SECONDS = 1209600;//14天
	/**
	 * 登录成功后存默认跳转页面
	 */
	public final static String LOGIN_SUCCESS_DEFAULT_TARGET_URL = "/admin/main";
	/**
	 * 登录失败后默认跳转的页面
	 */
	public final static String LOGIN_FAILURE_DEFAULT_TARGET_URL = "/admin/login";
	/**
	 * 登陆URL地址
	 */
	public final static String LOGIN_PAGE_URL = "/admin/login";
	/**
	 * 登出URL地址
	 */
	public final static String LOGOUT_PAGE_URL = "/admin/logout";
	/**
	 * SESSION失效跳转的页面
	 */
	public final static String	INVALID_SESSION_URL = "/admin/timeout";
	public final static String	EXPIRED_SESSION_URL = "/admin/expired";
	/**
	 * 用户同时登陆的次数
	 */
	public final static int MAXIMUM_SESSIONS = 1;
	/**
	 * 用户名不存在
	 */
	public final static String USER_NAME_NOT_FOUND = "用户不存在";
	/**
	 * 密码错误
	 */
	public final static String WRONG_PASSWORD = "用户名或者密码错误";
	/**
	 * 账户被锁
	 */
	public final static String USER_LOCKED = "账户已被锁定";
	/**
	 * 账户被禁用
	 */
	public final static String USER_DISABLED = "账户被禁用";
	/**
	 * 密码过期
	 */
	public final static String TIME_OUT_PASSWORD = "密码过期";
	/**
	 * 验证码错误
	 */
	public final static String WRONG_CAPTCHA = "验证码错误";
}
