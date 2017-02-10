package com.dx.ss.plugins.ptree.generate.java;

import org.apache.commons.lang3.StringUtils;

import com.dx.ss.plugins.ptree.utils.ColumnPropertyUtil;

public class FullyQualifiedJavaType {

	private String packageName;
	
	private String simpleName;

	private String primitiveTypeName;
	
	private boolean primitive = false;
	
	
	public FullyQualifiedJavaType(String packageName) {
		super();
		this.packageName = packageName;
		if(StringUtils.isNoneBlank(packageName)){
			String type = packageName.substring(packageName.lastIndexOf(".") + 1);
			this.simpleName = ColumnPropertyUtil.lowerFirstLetter(type);
		}
	}

	public FullyQualifiedJavaType(String packageName, String simpleName, boolean primitive) {
		super();
		this.packageName = packageName;
		this.simpleName = simpleName;
		this.primitive = primitive;
	}
	
	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getSimpleName() {
		return simpleName;
	}

	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}

	public String getPrimitiveTypeName() {
		if(StringUtils.isNoneBlank(primitiveTypeName)) return primitiveTypeName;
		if(!primitive) return StringUtils.EMPTY;
		if(StringUtils.isBlank(packageName) || !packageName.startsWith("java.lang")) return StringUtils.EMPTY;
		if(packageName.equals(Integer.class.getName())) return "int";
		if(packageName.equals(Long.class.getName())) return "long";
		if(packageName.equals(Short.class.getName())) return "short";
		if(packageName.equals(Float.class.getName())) return "float";
		if(packageName.equals(Double.class.getName())) return "double";
		if(packageName.equals(Boolean.class.getName())) return "boolean";
		if(packageName.equals(Byte.class.getName())) return "byte";
		return StringUtils.EMPTY;
	}

	public void setPrimitiveTypeName(String primitiveTypeName) {
		this.primitiveTypeName = primitiveTypeName;
	}

	public boolean isPrimitive() {
		return primitive;
	}

	public void setPrimitive(boolean primitive) {
		this.primitive = primitive;
	}
	
}
