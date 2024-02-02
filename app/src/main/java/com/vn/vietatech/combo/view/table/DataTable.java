package com.vn.vietatech.combo.view.table;


//DataTable này chỉ dùng để quản lý cái độ rộng Header
public class DataTable {
	private String colName;
	private int colWidth;
	private int colGravity;
	
	public DataTable(String name, int width, int gravity) {
		setColName(name);
		setColWidth(width);
		setColGravity(gravity);
	}

	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	public int getColWidth() {
		return colWidth;
	}

	public void setColWidth(int colWidth) {
		this.colWidth = colWidth;
	}

	public int getColGravity() {
		return colGravity;
	}

	public void setColGravity(int colGravity) {
		this.colGravity = colGravity;
	}
}
