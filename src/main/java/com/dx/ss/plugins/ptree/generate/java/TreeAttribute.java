package com.dx.ss.plugins.ptree.generate.java;

import org.apache.commons.lang3.StringUtils;

public enum TreeAttribute {
	ID("id"),
	NAME("name"),
	NLEVEL("nlevel"),
	LEFTID("leftId"),
	RIGHTID("rightId");

    private String attribute;

    private TreeAttribute(String attribute) {
        this.attribute = attribute;
    }

	public String getAttribute() {
		return attribute;
	}
	
	public static TreeAttribute getEnumByAttribute(String attribute) {
		if(!StringUtils.isBlank(attribute)){
	        for (TreeAttribute treeAttribute : TreeAttribute.values()) {
	            if (attribute.equals(treeAttribute.getAttribute())) {
	                return treeAttribute;
	            }
	        }
		}
        return null;
    }
}
