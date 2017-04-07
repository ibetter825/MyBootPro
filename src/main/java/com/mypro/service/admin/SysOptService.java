package com.mypro.service.admin;

import java.util.List;
import java.util.Map;

import com.mypro.bean.entity.admin.SysOpt;

/**
 * 系统列表操作按钮 service接口
 * @author user
 *
 */
public interface SysOptService {
	public List<SysOpt> queryOpts(SysOpt opt);
	/**
	 * 查询用户拥有的所有操作
	 * 查询用户的角色，查询用户的分组，再去关联right表，再查询出并集
	 * @param rq[userId, menuId]
	 * @return
	 */
	public List<SysOpt> queryOptsByUseAndMenu(Map<String, Integer> rq);
}
