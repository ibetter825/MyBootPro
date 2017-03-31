package com.mypro.service.admin.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mypro.bean.entity.admin.SysOpt;
import com.mypro.dao.admin.SysOptDao;
import com.mypro.service.admin.SysOptService;

@Service
public class SysOptServiceImpl implements SysOptService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass()); 
	
	@Autowired
	private SysOptDao sysOptDao;
	
	@Override
	public List<SysOpt> queryOpts(SysOpt opt) {
		return sysOptDao.select(opt);
	}

	@Override
	public List<SysOpt> queryUserOpts(Integer userId) {
		return null;
	}
}
