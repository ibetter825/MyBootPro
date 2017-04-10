package com.mypro.bean.rq;

import java.util.Map;
/**
 * 接收前台javabean对象
 * @author user
 *
 */
public class BeanRq {
	/**
	 * 查询的参数封到rq中
	 */
	private Map<String, Object> brq;

	public Map<String, Object> getBrq() {
		return brq;
	}

	public void setBrq(Map<String, Object> brq) {
		this.brq = brq;
	}

}
