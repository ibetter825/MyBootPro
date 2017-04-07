package com.mypro.dao.admin;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.mypro.bean.entity.admin.SysOpt;
import com.mypro.dao.BaseDao;

/**
 * 系统列表操作按钮 dao
 * @author user
 *
 */
public interface SysOptDao extends BaseDao<SysOpt> {
	/**
	 * 查询用户拥有的所有操作
	 * @param rq
	 * @return
	 */
	public List<Map<String, Object>> selectOptsByUser(Integer userId);
	/**
	 * 查询用户在某一菜单下的操作
	 * @param rq
	 * @return
	 */
	public List<SysOpt> selectOptsByUserAndMenu(Map<String, Integer> rq);
	
	@Select("select opt.*, menu.menu_route from sys_opt opt left join sys_menu menu on menu.menu_id = opt.menu_id")
	public List<Map<String, Object>> selectAllOpts();
}
