package com.mypro.bean.entity.admin;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import com.mypro.bean.entity.BaseEntity;


/**
 * 系统菜单
 * @author user
 *
 */
public class SysOpt extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer optId;
	private String optCode;
	private String optLabel;
	private String optDesc;
	private Short optState;
	private Integer menuId;
	private Integer optSeq;
	private String optIcon;
	private String optClass;
	private Short isShow;
	private String optType;
	private String optSuburl;
	private String optHandler;
	
	public Integer getOptId() {
		return optId;
	}
	public void setOptId(Integer optId) {
		this.optId = optId;
	}
	public String getOptCode() {
		return optCode;
	}
	public void setOptCode(String optCode) {
		this.optCode = optCode;
	}
	public String getOptLabel() {
		return optLabel;
	}
	public void setOptLabel(String optLabel) {
		this.optLabel = optLabel;
	}
	public String getOptDesc() {
		return optDesc;
	}
	public void setOptDesc(String optDesc) {
		this.optDesc = optDesc;
	}
	public Short getOptState() {
		return optState;
	}
	public void setOptState(Short optState) {
		this.optState = optState;
	}
	public Integer getMenuId() {
		return menuId;
	}
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
	public Integer getOptSeq() {
		return optSeq;
	}
	public void setOptSeq(Integer optSeq) {
		this.optSeq = optSeq;
	}
	public String getOptIcon() {
		return optIcon;
	}
	public void setOptIcon(String optIcon) {
		this.optIcon = optIcon;
	}
	public String getOptClass() {
		return optClass;
	}
	public void setOptClass(String optClass) {
		this.optClass = optClass;
	}
	public Short getIsShow() {
		return isShow;
	}
	public void setIsShow(Short isShow) {
		this.isShow = isShow;
	}
	public String getOptType() {
		return optType;
	}
	public void setOptType(String optType) {
		this.optType = optType;
	}
	public String getOptSuburl() {
		return optSuburl;
	}
	public void setOptSuburl(String optSuburl) {
		this.optSuburl = optSuburl;
	}
	public String getOptHandler() {
		return optHandler;
	}
	public void setOptHandler(String optHandler) {
		this.optHandler = optHandler;
	}
}