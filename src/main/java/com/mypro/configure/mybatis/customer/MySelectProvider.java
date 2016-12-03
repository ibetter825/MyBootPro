package com.mypro.configure.mybatis.customer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import tk.mybatis.mapper.entity.EntityTable;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

/**
 * 自定义查询工具
 * @author user
 *
 */
public class MySelectProvider extends MapperTemplate {

	public MySelectProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
		super(mapperClass, mapperHelper);
	}
	
	/**
     * 查询
     *
     * @param ms
     * @return
     */
    public String selectBySql(MappedStatement ms) {//参数只能是MappedStatement ms一个
        
        StringBuilder sql = new StringBuilder();
        sql.append("${sql}");
        
        /*<select id="superSelect" parameterType="String" resultType="java.util.LinkedHashMap"> 
        ${value} 
        </select>*/
        
        
        return sql.toString();
    }
}
