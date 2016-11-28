package com.mypro.dao.admin;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.mypro.bean.entity.QtTag;
import com.mypro.dao.BaseDao;
/**
 * example
 * @author user
 *
 */
public interface TagDao extends BaseDao<QtTag> {
	@Select("select * from qt_tag where tag_id = #{id}")
    public QtTag selectById(Integer id); // 使用注解
	public void save(QtTag tag);//使用xml配置文件，生成sql语句
	
	public List<QtTag> selectWithParams(Map<String, Object> rq);
}
