package com.mypro.service.admin;

import java.util.List;
import java.util.Map;

import com.mypro.bean.entity.admin.SysMenuConfig;

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
	/**
	 * 修改菜单配置文件
	 * @param config
	 * @return
	 */
	public boolean addOrEditConfig(SysMenuConfig config);
}
