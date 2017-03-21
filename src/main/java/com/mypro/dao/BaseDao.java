package com.mypro.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.mypro.configure.mybatis.mapper.MyBaseMapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 此接口不能被扫描到不然会出错
 * MyBaseMapper自定义接口
 * @author user
 *
 * @param <T>
 */
public interface BaseDao<T> extends Mapper<T>,MySqlMapper<T>,MyBaseMapper<T> {
	/**
	 * 传入sql查询结果 - mine
	 * @param sql
	 * @return
	 */
	@Select("${value}")
	public List<Map<String, Object>> selectBySql(String sql);
}
