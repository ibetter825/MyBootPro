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
}
