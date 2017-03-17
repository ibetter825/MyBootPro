package com.mypro.bean.entity.admin;

import javax.persistence.Id;

import com.mypro.bean.entity.BaseEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * 用于持久化 spring security数据到数据库
 * @author user
 *
 */
@Getter @Setter
public class SysPersistentLogins extends BaseEntity{
	private static final long serialVersionUID = 1L;
	@Id
	private String series;
	private String username;
	private String token;
	private Long last_used;
}