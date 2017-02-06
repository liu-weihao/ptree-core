package com.dx.ss.plugins.ptree.config;

import java.util.Properties;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class JpaConfigurationResolver extends BaseConfigurationResolver {

	@Override
	public BaseConfiguration resolve(Element rootNode) {
		JpaConfiguration configuration = new JpaConfiguration();
		super.commonResolve(configuration, rootNode);
		NodeList nodeList = rootNode.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            if ("reporitory".equals(node.getNodeName())) { 
            	parseReporitory(configuration, node);
            }
		}
		return configuration;
	}

	private void parseReporitory(JpaConfiguration configuration, Node node) {
		Properties attributes = super.parseAttributes(node);
		configuration.setReporitory(attributes.getProperty("reporitory"));
	}

}
