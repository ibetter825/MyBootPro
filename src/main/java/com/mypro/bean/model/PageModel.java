package com.mypro.bean.model;

import java.io.Serializable;
import java.util.List;

import com.github.pagehelper.Page;

/**
 * 列表结果
 * 默认插件格式
 * @author user
 *
 */
public class PageModel implements Serializable {
private static final long serialVersionUID = 1L;
	
	private Long total;
	private Integer pageSize;
	private Integer pageNumber;
	private Integer totalPages;
	private List<?> rows;
	private List<?> footer;
	
	public PageModel() {
		super();
	}
	
	public PageModel(Page<?> pager) {
		super();
		this.total = pager.getTotal();
		this.pageNumber = pager.getPageNum();
		this.pageSize = pager.getPageSize();
		this.totalPages = pager.getPages();
		this.rows = pager.getResult();
	}

	public PageModel(Long total, List<?> rows) {
		super();
		this.total = total;
		this.rows = rows;
	}

	public PageModel(Long total, List<?> rows, List<?> footer) {
		super();
		this.total = total;
		this.rows = rows;
		this.footer = footer;
	}

	public Long getTotal() {
		return total;
	}

	public PageModel setTotal(Long total) {
		this.total = total;
		return this;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public PageModel setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
		return this;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public PageModel setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
		return this;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public PageModel setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
		return this;
	}

	public List<?> getRows() {
		return rows;
	}

	public PageModel setRows(List<?> rows) {
		this.rows = rows;
		return this;
	}

	public List<?> getFooter() {
		return footer;
	}

	public PageModel setFooter(List<?> footer) {
		this.footer = footer;
		return this;
	}
}
