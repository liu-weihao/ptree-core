package com.dx.ss.plugins.ptree.config;

public class XmlMapper {

	private String model;
	
	private String sqlmap;

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getSqlmap() {
		return sqlmap;
	}

	public void setSqlmap(String sqlmap) {
		this.sqlmap = sqlmap;
	}

	@Override
	public String toString() {
		return "[model=" + model + ", sqlmap=" + sqlmap + "]";
	}
}
