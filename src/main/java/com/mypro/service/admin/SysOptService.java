package com.mypro.service.admin;

import java.util.List;
import com.mypro.bean.entity.admin.SysOpt;

/**
 * 系统列表操作按钮 service接口
 * @author user
 *
 */
public interface SysOptService {
	public List<SysOpt> queryOpts(SysOpt opt);
	/**
	 * 查询拥有的所有操作
	 * @param userId
	 * @return
	 */
	public List<SysOpt> queryUserOpts(Integer userId);
}
