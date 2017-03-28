package com.mypro.bean.entity.admin;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import com.mypro.bean.entity.BaseEntity;

/**
 * 系统操作日志
 * @author user
 *
 */
public class SysOptLog extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer optLogId;
	private Long optDateTime;
	private Integer optUserId;
	private String optLogCont;
	private Short optLogType;
	
	public Integer getOptLogId() {
		return optLogId;
	}
	public void setOptLogId(Integer optLogId) {
		this.optLogId = optLogId;
	}
	public Long getOptDateTime() {
		return optDateTime;
	}
	public void setOptDateTime(Long optDateTime) {
		this.optDateTime = optDateTime;
	}
	public Integer getOptUserId() {
		return optUserId;
	}
	public void setOptUserId(Integer optUserId) {
		this.optUserId = optUserId;
	}
	public String getOptLogCont() {
		return optLogCont;
	}
	public void setOptLogCont(String optLogCont) {
		this.optLogCont = optLogCont;
	}
	public Short getOptLogType() {
		return optLogType;
	}
	public void setOptLogType(Short optLogType) {
		this.optLogType = optLogType;
	}
}
