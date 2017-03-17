package com.mypro.bean.entity.admin;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotEmpty;

import com.mypro.bean.entity.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
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
}