package com.mypro.configure.mybatis.mapper;

import org.apache.ibatis.annotations.InsertProvider;
import com.mypro.configure.mybatis.customer.MyInsertProvider;

public interface MyInsertMapper<T> {
    /**
     * 保存或修改一个实体，null的属性不会保存，会使用数据库默认值
     * 暂时只使用与MYSQL
     * @param record
     * @return
     */
    @InsertProvider(type = MyInsertProvider.class, method = "dynamicSQL")
    int insertOrUpdateSelective(T record);
}
