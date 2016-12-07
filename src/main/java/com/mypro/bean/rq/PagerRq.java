package com.mypro.bean.rq;

import com.mypro.bean.constant.QueryConstant;

/**
 * 分页查询
 * @author user
 *
 */
public class PagerRq {
	/**
	 * 当前页码
	 */
	private Integer page =	Integer.valueOf(QueryConstant.DEFAULT_PAGENUMBER);
	/**
	 * 显示数据量
	 */
	private Integer size = Integer.valueOf(QueryConstant.DEFAULT_PAGESIZE);
	
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
}
