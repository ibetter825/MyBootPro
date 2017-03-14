package com.mypro.bean.entity.admin;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotEmpty;

import com.mypro.bean.entity.BaseEntity;

public class QtTag extends BaseEntity{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="tag_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer tag_id;
	@NotEmpty(message="标签名不能为空")
	private String tag_name;
	@Min(value = 1, message="数字必须超过1")
	private Integer tag_num;
	private Long add_time;
	public Integer getTag_id() {
		return tag_id;
	}
	public void setTag_id(Integer tag_id) {
		this.tag_id = tag_id;
	}
	public String getTag_name() {
		return tag_name;
	}
	public void setTag_name(String tag_name) {
		this.tag_name = tag_name;
	}
	public Integer getTag_num() {
		return tag_num;
	}
	public void setTag_num(Integer tag_num) {
		this.tag_num = tag_num;
	}
	public Long getAdd_time() {
		return add_time;
	}
	public void setAdd_time(Long add_time) {
		this.add_time = add_time;
	}
}