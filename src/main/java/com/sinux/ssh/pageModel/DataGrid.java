package com.sinux.ssh.pageModel;

import java.util.ArrayList;
import java.util.List;

/**
 * EasyUI DataGrid模型
 * 
 */
public class DataGrid<T> implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long total = 0L;
	private List<T> rows = new ArrayList<T>();

	public DataGrid() {
		super();
	}

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
