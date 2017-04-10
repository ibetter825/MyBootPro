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
	@SuppressWarnings("unused")
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SysMenuConfigDao smcDao;
	
	@Override
	public List<Map<String, Object>> queryTable(String schemaName) {
		String sql = "select TABLE_NAME, TABLE_COMMENT from information_schema.tables where table_schema='"+schemaName+"'";
		return smcDao.selectBySql(sql);
	}
	
	@Override
	public List<Map<String, Object>> queryColumns(String tableName) {
		String sql = "select COLUMN_NAME, COLUMN_TYPE, DATA_TYPE, ORDINAL_POSITION, COLUMN_DEFAULT, IS_NULLABLE, COLUMN_COMMENT, COLUMN_KEY from information_schema.columns where table_schema='boot' and table_name = '"+ tableName +"'";
		return smcDao.selectBySql(sql);
	}

	@Override
	public List<Map<String, Object>> queryListBySql(String sql) {
		return smcDao.selectBySql(sql);
	}
	
	@Override
	public boolean addBeanBySql(String sql) {
		return smcDao.insertBySql(sql) == 1;
	}

	@Override
	public boolean editBeanBySql(String sql) {
		return smcDao.updateBySql(sql) == 1;
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
