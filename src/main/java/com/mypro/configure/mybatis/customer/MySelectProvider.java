package com.mypro.configure.mybatis.customer;

import org.apache.ibatis.mapping.MappedStatement;
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
    public String selectMap(MappedStatement ms) {//参数只能是MappedStatement ms一个
    	final Class<?> entityClass = getEntityClass(ms);//需要判断传入的entity是否属于map或者BaseEntity，返回的类型为Map
    	
    	//setResultType(ms, entityClass);//传入的entityClass必须在数据库中有对应的表，要修改
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.selectAllColumns(entityClass));
        sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
        sql.append(SqlHelper.whereAllIfColumns(entityClass, isNotEmpty()));
        
        return sql.toString();
    }
}
