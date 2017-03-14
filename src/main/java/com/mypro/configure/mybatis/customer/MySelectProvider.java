package com.mypro.configure.mybatis.customer;

import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;

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
