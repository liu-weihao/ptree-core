package com.dx.ss.plugins.ptree.config;

public class Mybatis3Configuration extends BaseConfiguration {

	private XmlMapper xmlMapper;

	public XmlMapper getXmlMapper() {
		return xmlMapper;
	}

	public void setXmlMapper(XmlMapper xmlMapper) {
		this.xmlMapper = xmlMapper;
	}

	@Override
	public String toString() {
		return "MybatisConfiguration ["+super.toString()+", xmlMapper=" + xmlMapper + "]";
	}
}
