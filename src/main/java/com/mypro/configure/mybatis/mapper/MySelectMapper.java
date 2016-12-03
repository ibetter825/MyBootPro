package com.mypro.configure.mybatis.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import com.mypro.configure.mybatis.customer.MySelectProvider;

public interface MySelectMapper<T> {
	/**
     * 直接查询sql返回map
     *
     * @param sql 查询的语句
     * @return
     */
    @SuppressWarnings("rawtypes")
	@SelectProvider(type = MySelectProvider.class, method = "dynamicSQL")
    List selectBySql(@Param("sql") String sql);
}
