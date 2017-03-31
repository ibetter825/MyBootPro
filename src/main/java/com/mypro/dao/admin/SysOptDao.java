package com.mypro.dao.admin;

import java.util.List;
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
	public List<SysOpt> selectUserOpts(Integer userId);
}
