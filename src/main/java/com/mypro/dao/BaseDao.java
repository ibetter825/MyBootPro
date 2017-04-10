package com.mypro.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
	/**
	 * 传入sql修改数据
	 * @param sql
	 * @return
	 */
	@Update("${value}")
	public int updateBySql(String sql);
	/**
	 * 传入sql插入数据
	 * @param sql
	 * @return
	 */
	@Insert("${value}")
	public int insertBySql(String sql);
}
