package com.dx.ss.plugins.ptree.generate.java;

import org.apache.commons.lang3.StringUtils;

import com.dx.ss.plugins.ptree.utils.ColumnPropertyUtil;

public class Parameter {

	private FullyQualifiedJavaType type;
	
	private String name;

	public Parameter(FullyQualifiedJavaType type) {
		super();
		this.type = type;
	}

	public Parameter(FullyQualifiedJavaType type, String name) {
		super();
		this.type = type;
		this.name = name;
	}

	public FullyQualifiedJavaType getType() {
		return type;
	}

	public void setType(FullyQualifiedJavaType type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFormattedContent() {
		String content = ColumnPropertyUtil.upperFirstLetter(type.getSimpleName()) ;
		if(StringUtils.isBlank(name))	name = ColumnPropertyUtil.lowerFirstLetter(content);
		content += " ";
		content += name;
		return content;
	}
	
}
