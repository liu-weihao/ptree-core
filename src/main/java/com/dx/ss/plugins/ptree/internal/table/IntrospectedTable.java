package com.dx.ss.plugins.ptree.internal.table;

import java.util.List;

import com.dx.ss.plugins.ptree.config.TableConfiguration;

public class IntrospectedTable {
	
	private TableConfiguration table;
	
	/** The primary key column. */
    private IntrospectedColumn primaryKeyColumn;
    
	/** The base columns. */
    private List<IntrospectedColumn> baseColumns;
    
    /** The blob columns. */
    private List<IntrospectedColumn> blobColumns;
    
    /**
     * Table remarks retrieved from database metadata
     */
    private String remarks;

	public TableConfiguration getTable() {
		return table;
	}

	public void setTable(TableConfiguration table) {
		this.table = table;
	}

	public IntrospectedColumn getPrimaryKeyColumn() {
		return primaryKeyColumn;
	}

	public void setPrimaryKeyColumn(IntrospectedColumn primaryKeyColumn) {
		this.primaryKeyColumn = primaryKeyColumn;
	}

	public List<IntrospectedColumn> getBaseColumns() {
		return baseColumns;
	}

	public void setBaseColumns(List<IntrospectedColumn> baseColumns) {
		this.baseColumns = baseColumns;
	}

	public List<IntrospectedColumn> getBlobColumns() {
		return blobColumns;
	}

	public void setBlobColumns(List<IntrospectedColumn> blobColumns) {
		this.blobColumns = blobColumns;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
