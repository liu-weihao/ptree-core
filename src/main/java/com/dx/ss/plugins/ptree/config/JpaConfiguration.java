package com.dx.ss.plugins.ptree.config;

public class JpaConfiguration extends BaseConfiguration {

	private String reporitory;

	public String getReporitory() {
		return reporitory;
	}

	public void setReporitory(String reporitory) {
		this.reporitory = reporitory;
	}

	@Override
	public String toString() {
		return "JpaConfiguration ["+super.toString()+", reporitory=" + reporitory + "]";
	}
}
