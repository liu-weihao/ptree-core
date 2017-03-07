package com.dx.ss.plugins.ptree.generate.java;

import java.util.List;
import java.util.Map;

public class JavaAttribute {

	private JavaVisibility visibility = JavaVisibility.PRIVATE;
	
	private FullyQualifiedJavaType type;
	
	private List<Map<String, FullyQualifiedJavaType>> annotions;
	
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

	public List<Map<String, FullyQualifiedJavaType>> getAnnotions() {
		return annotions;
	}

	public void addAnnotion(Map<String, FullyQualifiedJavaType> annotion) {
		this.annotions.add(annotion);
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
