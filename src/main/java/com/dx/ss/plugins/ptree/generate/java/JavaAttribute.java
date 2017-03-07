package com.dx.ss.plugins.ptree.generate.java;

import java.util.List;
import java.util.Map;

public class JavaAttribute {

	private JavaVisibility visibility = JavaVisibility.PRIVATE;
	
	private FullyQualifiedJavaType type;
	
	private List<Map<String, FullyQualifiedJavaType>> attributeAnnotions;
	
	private String attributeName;
	
	private String comments;
	
	public JavaAttribute() {
		super();
	}

	public JavaAttribute(JavaVisibility visibility, FullyQualifiedJavaType type, String attributeName) {
		super();
		this.visibility = visibility;
		this.type = type;
		this.attributeName = attributeName;
	}

	public JavaVisibility getVisibility() {
		return visibility;
	}

	public void setVisibility(JavaVisibility visibility) {
		this.visibility = visibility;
	}

	public FullyQualifiedJavaType getType() {
		return type;
	}

	public void setType(FullyQualifiedJavaType type) {
		this.type = type;
	}
	 
	public List<Map<String, FullyQualifiedJavaType>> getAttributeAnnotions() {
		return attributeAnnotions;
	}

	public void setAttributeAnnotions(List<Map<String, FullyQualifiedJavaType>> attributeAnnotions) {
		this.attributeAnnotions = attributeAnnotions;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
}
