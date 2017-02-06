package com.dx.ss.plugins.ptree.config;

import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public abstract class BaseConfigurationResolver {

	public abstract BaseConfiguration resolve(Element rootNode);
	
	public void commonResolve(BaseConfiguration configuration, Element rootNode){
		if(configuration == null || rootNode == null)	return;
		NodeList nodeList = rootNode.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            if ("packages".equals(node.getNodeName())) { 
            	parsePackages(configuration, node);
            } else if ("classPathEntry".equals(node.getNodeName())) { 
            	parseClassPathEntry(configuration, node);
            } else if ("jdbcConnection".equals(node.getNodeName())) { 
            	parseJdbcConnection(configuration, node);
            } else if ("tableConfiguration".equals(node.getNodeName())) { 
            	parseTableConfiguration(configuration, node);
            }
        }
	}

	protected void parsePackages(BaseConfiguration configuration, Node node) {
		PackageConfiguration packageConfiguration = new PackageConfiguration();
		
		Properties attributes = parseAttributes(node);
		packageConfiguration.setBasePackage(attributes.getProperty("base"));
		packageConfiguration.setBeanSubPackage(attributes.getProperty("bean"));
		packageConfiguration.setServiceSubPackage(attributes.getProperty("service"));
		packageConfiguration.setDaoSubPackage(attributes.getProperty("dao"));
		
		configuration.setPackageConfiguration(packageConfiguration);
	}
	
	protected void parseClassPathEntry(BaseConfiguration configuration, Node node) {
		Properties attributes = parseAttributes(node);
		configuration.setClassPathEntry(attributes.getProperty("location"));
	}
	
	protected void parseJdbcConnection(BaseConfiguration configuration, Node node) {
		JDBCConnectionConfiguration jdbc = new JDBCConnectionConfiguration();

        Properties attributes = parseAttributes(node);
        String driverClass = attributes.getProperty("driverClass");
        String connectionURL = attributes.getProperty("connectionURL");
        String user = attributes.getProperty("user");
        String password = attributes.getProperty("password");

        if (StringUtils.isNoneBlank(driverClass)) {
        	jdbc.setDriverClass(driverClass);
        }
        
        if (StringUtils.isNoneBlank(connectionURL)) {
        	jdbc.setConnectionURL(connectionURL);
        }

        if (StringUtils.isNoneBlank(user)) {
            jdbc.setUser(user);
        }

        if (StringUtils.isNoneBlank(password)) {
            jdbc.setPassword(password);
        }
		
        configuration.setJdbcConnectionConfiguration(jdbc);
	}
	
	protected void parseTableConfiguration(BaseConfiguration configuration, Node node){
		TableConfiguration tc = new TableConfiguration();
		
		Properties attributes = parseAttributes(node);
		String schema = attributes.getProperty("schema");
        String tableName = attributes.getProperty("tableName");
        String beanName = attributes.getProperty("beanName");
        String enableComments = attributes.getProperty("enableComments");
        
        if(StringUtils.isNoneBlank(schema)){
        	tc.setSchema(schema);
        }
        
        if(StringUtils.isNoneBlank(tableName)){
        	tc.setTableName(tableName);
        }
        
        if(StringUtils.isNoneBlank(beanName)){
        	tc.setBeanName(beanName);
        }
        
        if(StringUtils.isNoneBlank(enableComments)){
        	tc.setEnableComments(Boolean.valueOf(enableComments));
        }
        
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node childNode = nodeList.item(i);

            if (childNode.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            
            if ("ignores".equals(childNode.getNodeName())) {
                parseIgnoreColumns(tc, childNode);
            }
        }
		configuration.addTableConfiguration(tc);
	}

	private void parseIgnoreColumns(TableConfiguration tc, Node node) {
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node childNode = nodeList.item(i);

            if (childNode.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            if ("column".equals(childNode.getNodeName())) { //$NON-NLS-1$
            	Properties attributes = parseAttributes(childNode);
                tc.addIgnoredColumn(attributes.getProperty("value"));
            }
        }
	}

	protected Properties parseAttributes(Node node) {
        Properties attributes = new Properties();
        NamedNodeMap nnm = node.getAttributes();
        for (int i = 0; i < nnm.getLength(); i++) {
            Node attribute = nnm.item(i);
            attributes.put(attribute.getNodeName(), attribute.getNodeValue());
        }
        return attributes;
    }
}
