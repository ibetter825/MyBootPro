package com.mypro.bean.entity.admin;

import javax.persistence.Id;

import com.mypro.bean.entity.BaseEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * 系统用户角色
 * @author user
 *
 */
@Getter @Setter
public class SysRole extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	@Id
	private String roleNo;
	private String roleName;
	private String rolePno;
	private String roleDesc;
	private Short roleState;
}
