package com.dx.ss.plugins.ptree.generate.java;

public class JavaAttribute {

	private JavaVisibility visibility = JavaVisibility.PRIVATE;
	
	private FullyQualifiedJavaType type;
	
	private String attributeName;

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

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	
}
