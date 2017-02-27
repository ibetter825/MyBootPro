package com.mypro.bean.rq;

import org.apache.commons.lang3.StringUtils;

import com.mypro.bean.constant.QueryConstant;

/**
 * 分页查询
 * @author user
 *
 */
public class PagerRq {
	/**
	 * 当前页码, 默认
	 */
	private Integer page =	Integer.valueOf(QueryConstant.DEFAULT_PAGENUMBER);
	/**
	 * 显示数据量, 默认
	 */
	private Integer size = Integer.valueOf(QueryConstant.DEFAULT_PAGESIZE);
	/**
	 * 排序字段，单个字段的排序，使用此对象中的方法
	 */
	private String sort;
	/**
	 * 排序呢方式
	 */
	private String order;
	
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getOrder() {
		if(StringUtils.isNotEmpty(this.sort))
			return this.sort + " " + order;
		else
			return null;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	
}
