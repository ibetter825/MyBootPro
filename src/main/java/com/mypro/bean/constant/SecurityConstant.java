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
	public final static String LOGIN_SUCCESS_DEFAULT_TARGET_URL = "/admin/index.html";
	/**
	 * 登录失败后默认跳转的页面
	 */
	public final static String LOGIN_FAILURE_DEFAULT_TARGET_URL = "/admin/login.html";
	/**
	 * 登陆URL地址
	 */
	public final static String LOGIN_PAGE_URL = "/admin/login.html";
	/**
	 * 登出URL地址
	 */
	public final static String LOGOUT_PAGE_URL = "/admin/logout.html";
	/**
	 * SESSION失效跳转的页面
	 */
	public final static String	INVALID_SESSION_URL = "/admin/login/timeout.html";
	public final static String	EXPIRED_SESSION_URL = "/admin/login/expired.html";
	/**
	 * 用户同时登陆的次数
	 */
	public final static int MAXIMUM_SESSIONS = 1;
	/**
	 * 用户名不存在
	 */
	public final static String USER_NAME_NOT_FOUND_MSG = "用户不存在";
	/**
	 * 查询用户的操作出错
	 */
	public final static String USER_RIGHT_SELECT_FAIL = "用户权限查询失败";
	public final static String USER_RIGHT_INIT_FAIL = "用户权限处理失败";
	/**
	 * 密码错误
	 */
	public final static String WRONG_PASSWORD_MSG = "用户名或者密码错误";
	/**
	 * 账户被锁
	 */
	public final static String USER_LOCKED_MSG = "账户已被锁定";
	/**
	 * 账户被禁用
	 */
	public final static String USER_DISABLED_MSG = "账户被禁用";
	/**
	 * 密码过期
	 */
	public final static String TIME_OUT_PASSWORD_MSG = "密码过期";
	/**
	 * 登录过期
	 */
	public final static String TIME_OUT_SESSION_MSG = "登录过期";
	/**
	 * 多次登录
	 */
	public final static String EXPIRED_SESSION_MSG = "您的账户已在另一地点登录";
	/**
	 * 验证码错误
	 */
	public final static String WRONG_CAPTCHA_MSG = "验证码错误";
}
