package com.mypro.service.admin;

import java.util.List;
import java.util.Map;

import com.mypro.bean.entity.QtTag;
import com.mypro.bean.rq.QueryRq;

public interface TagService {
	public QtTag selectTagById(Integer id);
	public QtTag selectTagByIdFromDs1(Integer id);
	public boolean insertTag(QtTag tag);
	public void saveTag(QtTag tag);
	/**
	 * 分页demo
	 * @return
	 */
	public List<QtTag> select();
	/**
	 * ehcache demo
	 * @return
	 */
	public List<QtTag> selectAllWithCache();
	/**
	 * 根据查询条件查询
	 * @param rq
	 * @return
	 */
	public List<QtTag> selectWithParams(QueryRq rq);
	
	public Map selectMap(QtTag tag);
}
