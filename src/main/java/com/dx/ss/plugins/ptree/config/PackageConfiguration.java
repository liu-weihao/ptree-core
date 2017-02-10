package com.dx.ss.plugins.ptree.config;

public class PackageConfiguration {

	private String basePackage;
	
	private String beanSubPackage = "bean";
	
	private String serviceSubPackage = "service";
	
	private String daoSubPackage = "dao";

	public String getBasePackage() {
		return basePackage;
	}

	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}

	public String getBeanSubPackage() {
		return beanSubPackage;
	}

	public void setBeanSubPackage(String beanSubPackage) {
		this.beanSubPackage = beanSubPackage;
	}

	public String getServiceSubPackage() {
		return serviceSubPackage;
	}

	public void setServiceSubPackage(String serviceSubPackage) {
		this.serviceSubPackage = serviceSubPackage;
	}

	public String getDaoSubPackage() {
		return daoSubPackage;
	}

	public void setDaoSubPackage(String daoSubPackage) {
		this.daoSubPackage = daoSubPackage;
	}

	@Override
	public String toString() {
		return "[basePackage=" + basePackage + ", beanSubPackage=" + beanSubPackage
				+ ", serviceSubPackage=" + serviceSubPackage + ", daoSubPackage=" + daoSubPackage + "]";
	}
	
}
