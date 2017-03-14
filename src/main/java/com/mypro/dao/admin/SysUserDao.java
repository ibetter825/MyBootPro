package com.mypro.dao.admin;

import java.util.Map;

import com.mypro.bean.entity.admin.SysUser;
import com.mypro.dao.BaseDao;
/**
 * 系统用户 dao
 * @author user
 *
 */
public interface SysUserDao extends BaseDao<SysUser> {
	public Map<String, Object> selectUserByUserName(String user_name);
}
