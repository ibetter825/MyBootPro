package com.mypro.configure.mybatis.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.SelectProvider;
import com.mypro.configure.mybatis.customer.MySelectProvider;

public interface MySelectMapper<T> {
	/**
     * 根据实体中的属性进行查询，只能有一个返回值，有多个结果是抛出异常，查询条件使用等号
     *
     * @param record
     * @return
     */
    @SuppressWarnings("rawtypes")
	@SelectProvider(type = MySelectProvider.class, method = "dynamicSQL")
    Map selectMap(T rq);
}
