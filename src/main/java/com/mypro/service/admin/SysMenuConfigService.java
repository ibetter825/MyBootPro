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
	public List<Map<String, Object>> queryTable(String schemaName);
	/**
	 * 查询表的字段
	 * @param table
	 * @return
	 */
	public List<Map<String, Object>> queryColumns(String tableName);
	/**
	 * 传入sql查询结果
	 * @param sql
	 * @return
	 */
	public List<Map<String, Object>> queryListBySql(String sql);
	/**
	 * 直接传入sql修改数据
	 * @param sql
	 * @return
	 */
	public boolean editBeanBySql(String sql);
	/**
	 * 直接传入sql增加数据
	 * @param sql
	 * @return
	 */
	public boolean addBeanBySql(String sql);
	
	/**
	 * 修改菜单配置文件
	 * @param config
	 * @return
	 */
	public boolean addOrEditConfig(SysMenuConfig config);
	/**
	 * 查询单个config
	 * @param config
	 * @return
	 */
	public SysMenuConfig queryConfig(SysMenuConfig config);
}
