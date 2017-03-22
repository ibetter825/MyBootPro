package com.mypro.service.admin.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mypro.bean.entity.admin.SysMenuConfig;
import com.mypro.dao.admin.SysMenuConfigDao;
import com.mypro.service.admin.SysMenuConfigService;

@Service
public class SysMenuConfigServiceImpl implements SysMenuConfigService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SysMenuConfigDao smcDao;
	
	@Override
	public List<Map<String, Object>> queryTable(String sql) {
		return smcDao.selectBySql(sql);
	}

	@Override
	public boolean addOrEditConfig(SysMenuConfig config) {
		int res = smcDao.insertOrUpdateSelective(config);
		return res == 1 || res == 2;
	}

	@Override
	public SysMenuConfig queryConfig(SysMenuConfig config) {
		return smcDao.selectOne(config);
	}  
	
}
