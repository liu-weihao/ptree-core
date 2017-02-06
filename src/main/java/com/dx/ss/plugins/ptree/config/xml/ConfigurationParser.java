package com.dx.ss.plugins.ptree.config.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.dx.ss.plugins.ptree.config.BaseConfiguration;
import com.dx.ss.plugins.ptree.config.BaseConfigurationResolver;
import com.dx.ss.plugins.ptree.config.JpaConfigurationResolver;
import com.dx.ss.plugins.ptree.config.Mybatis3ConfigurationResolver;

public class ConfigurationParser {

	private BaseConfigurationResolver resolver;
	
	public BaseConfiguration parseConfiguration(File inputFile) throws IOException {

		if(inputFile == null || inputFile.isDirectory())	return null;
	
		FileInputStream fis = new FileInputStream(inputFile);
		return parseConfiguration(fis);
	}
	
	public BaseConfiguration parseConfiguration(InputStream stream) throws IOException{
		try {
			InputSource is = new InputSource(stream);
			return parseConfiguration(is);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(stream != null)	stream.close();
		}
		return null;
	}
	
	private BaseConfiguration parseConfiguration(InputSource inputSource) {

		if(inputSource == null)	return null;
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(inputSource);
			Element rootNode = document.getDocumentElement();
            if (rootNode.getNodeType() == Node.ELEMENT_NODE && rootNode.hasAttribute("type")){//The node is an Element.
            	String generateType = rootNode.getAttribute("type");
				if(generateType.equalsIgnoreCase("jpa")){
					resolver = new JpaConfigurationResolver();
            	}else if(generateType.equalsIgnoreCase("mybatis3")){
            		resolver = new Mybatis3ConfigurationResolver();
            	}
				return resolver.resolve(rootNode);
            }
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
