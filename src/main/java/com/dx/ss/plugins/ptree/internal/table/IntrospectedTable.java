package com.dx.ss.plugins.ptree.internal.table;

import java.util.ArrayList;
import java.util.List;

import com.dx.ss.plugins.ptree.config.TableConfiguration;

public class IntrospectedTable {
	
	private String fileName;
	
	private TableConfiguration table;
	
	/** The primary key column. */
    private IntrospectedColumn primaryKeyColumn;
    
	/** The base columns. */
    private List<IntrospectedColumn> baseColumns;
    
    /** The blob columns. */
    private List<IntrospectedColumn> blobColumns;
    
    private String fullQualifiedJavaType; 
    
    /**
     * Table remarks retrieved from database metadata
     */
    private String remarks;

	public IntrospectedTable() {
		super();
		baseColumns = new ArrayList<>();
		blobColumns = new ArrayList<>();
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

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

	public String getFullQualifiedJavaType() {
		return fullQualifiedJavaType;
	}

	public void setFullQualifiedJavaType(String fullQualifiedJavaType) {
		this.fullQualifiedJavaType = fullQualifiedJavaType;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public List<IntrospectedColumn> getAllColumns(){
		List<IntrospectedColumn> allColumns = new ArrayList<>();
		if(primaryKeyColumn != null) allColumns.add(0, primaryKeyColumn);
		allColumns.addAll(baseColumns);
		allColumns.addAll(blobColumns);
		return allColumns;
	}
}
