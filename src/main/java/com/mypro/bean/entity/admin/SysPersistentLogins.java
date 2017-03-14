package com.mypro.bean.entity.admin;

import javax.persistence.Id;

import com.mypro.bean.entity.BaseEntity;

/**
 * 用于持久化 spring security数据到数据库
 * @author user
 *
 */
public class SysPersistentLogins extends BaseEntity{
	private static final long serialVersionUID = 1L;
	@Id
	private String series;
	private String username;
	private String token;
	private Long last_used;
	
	public String getSeries() {
		return series;
	}
	public void setSeries(String series) {
		this.series = series;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Long getLast_used() {
		return last_used;
	}
	public void setLast_used(Long last_used) {
		this.last_used = last_used;
	}
	
}