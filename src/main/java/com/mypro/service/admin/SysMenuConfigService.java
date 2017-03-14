package com.mypro.service.admin;

import java.util.List;
import java.util.Map;

/**
 * 系统菜单 service接口
 * @author user
 *
 */
public interface SysMenuConfigService {
	/**
	 * 查询schema中的表列表或者所有表
	 * @param sql
	 * @return
	 */
	public List<Map<String, Object>> queryTable(String sql);
}
