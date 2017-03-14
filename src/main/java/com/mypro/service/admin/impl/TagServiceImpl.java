package com.mypro.service.admin.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.mypro.annotation.DataSource;
import com.mypro.bean.entity.admin.QtTag;
import com.mypro.bean.rq.QueryRq;
import com.mypro.dao.admin.TagDao;
import com.mypro.service.admin.TagService;

@Service
public class TagServiceImpl implements TagService {
	@Autowired
	private TagDao tagDao;
	
	@Override
	public QtTag selectTagById(Integer id) {
		return tagDao.selectById(id);
	}

	@Override
	@DataSource(name="ds1")
	public QtTag selectTagByIdFromDs1(Integer id) {
		return tagDao.selectById(id);
	}

	@Override
	@Transactional
	public boolean insertTag(QtTag tag) {
		tagDao.insertSelective(tag);
		//Integer.valueOf("s");
		return true;
	}

	@Override
	@CacheEvict(value = "demo", key = "'tags'")//保存时清除缓存
	public void saveTag(QtTag tag) {
		tagDao.save(tag);
	}

	@Override
	public List<QtTag> select() {
		List<QtTag> tags = tagDao.selectAll();
		return tags;
	}

	@Override
	@Cacheable(value = "demo", key = "'tags'")//查询时设置缓存
	public List<QtTag> selectAllWithCache() {
		System.err.println("--查询数据库，还没有进入缓存");
		List<QtTag> tags = tagDao.selectAll();
		return tags;
	}

	@Override
	public List<QtTag> selectWithParams(QueryRq rq) {
		return tagDao.selectWithParams(rq.getRq());
	}

	@Override
	public Map selectMap(QtTag tag) {
		return null;
		//return tagDao.selectOne(tag, HashMap.class);
	}

}
