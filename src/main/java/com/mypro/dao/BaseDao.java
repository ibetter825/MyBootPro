package com.mypro.dao;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 此接口不能被扫描到不然会出错
 * @author user
 *
 * @param <T>
 */
public interface BaseDao<T> extends Mapper<T>,MySqlMapper<T> {
	
}
