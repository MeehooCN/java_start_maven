package com.meehoo.biz.core.basic.param;

import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class PageResult<T> {

	public PageResult(Long total, List<T> rows) {
		this.total = total;
		this.rows = rows;
	}
	
	private Long total;
	private List<T> rows;
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	
}
