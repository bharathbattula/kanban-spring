package com.ansell.ask.util.grid;

public class GridDetails {

	private String gridId=null;
	private String gridName=null;
	private String gridDescription=null;
	private String tableName=null;
	private String columnNames=null;
	private Integer queryImpl=null;
	
	public String getGridId() {
		return gridId;
	}
	public String getColumnNames() {
		return columnNames;
	}
	public void setColumnNames(String columnNames) {
		this.columnNames = columnNames;
	}
	public void setGridId(String gridId) {
		this.gridId = gridId;
	}
	public String getGridName() {
		return gridName;
	}
	public void setGridName(String gridName) {
		this.gridName = gridName;
	}
	public String getGridDescription() {
		return gridDescription;
	}
	public void setGridDescription(String gridDescription) {
		this.gridDescription = gridDescription;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public Integer getQueryImpl() {
		return queryImpl;
	}
	public void setQueryImpl(Integer queryImpl) {
		this.queryImpl = queryImpl;
	}
	
	
}
