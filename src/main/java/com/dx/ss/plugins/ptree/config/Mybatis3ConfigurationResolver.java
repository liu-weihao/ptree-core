package com.dx.ss.plugins.ptree.config;

import java.util.Properties;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Mybatis3ConfigurationResolver extends BaseConfigurationResolver {

	@Override
	public BaseConfiguration resolve(Element rootNode) {
		Mybatis3Configuration configuration = new Mybatis3Configuration();
		super.commonResolve(configuration, rootNode);
		NodeList nodeList = rootNode.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            if ("mapper".equals(node.getNodeName())) { 
            	parseMapper(configuration, node);
            }
		}
		return configuration;
	}

	private void parseMapper(Mybatis3Configuration configuration, Node node) {
		XmlMapper xmlMapper = new XmlMapper();
		
		Properties attributes = super.parseAttributes(node);
		xmlMapper.setModel(attributes.getProperty("model"));
		xmlMapper.setSqlmap(attributes.getProperty("sqlmap"));
		
		configuration.setXmlMapper(xmlMapper);
	}

}
