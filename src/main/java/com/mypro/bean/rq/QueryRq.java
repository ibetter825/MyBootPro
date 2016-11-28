package com.mypro.bean.rq;

import java.util.Map;
/**
 * 通过查询Bean
 * @author user
 *
 */
public class QueryRq {
	private Map<String, Object> rq;
	public Map<String, Object> getRq() {
		return rq;
	}
	public void setRq(Map<String, Object> rq) {
		this.rq = rq;
	}
}
