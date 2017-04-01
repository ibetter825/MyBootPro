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
	 * 通过参数查询
	 * @param rq
	 * @return
	 */
	public List<Map<String, Object>> selectUserOpts(Integer userId);
	@Select("select opt.*, menu.menu_code from sys_opt opt left join sys_menu menu on menu.menu_id = opt.menu_id")
	public List<Map<String, Object>> selectAllOpts();
}
