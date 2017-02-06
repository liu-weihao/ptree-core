/**
 *    Copyright 2006-2016 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.dx.ss.plugins.ptree.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.dx.ss.plugins.ptree.config.xml.Attribute;
import com.dx.ss.plugins.ptree.config.xml.XmlElement;

public class TableConfiguration {
    
    /** The comments from table columns. */
    private boolean enableComments;

    /** The ignored columns. */
    private List<String> ignoredColumns = new ArrayList<>();

    /** The schema. */
    private String schema;
    
    /** The table name. */
    private String tableName;
    
    /** The domain object name. */
    private String beanName;


    /**
     * To xml element.
     *
     * @return the xml element
     */
    public XmlElement toXmlElement() {
        XmlElement xmlElement = new XmlElement("table"); //$NON-NLS-1$
        xmlElement.addAttribute(new Attribute("tableName", tableName)); //$NON-NLS-1$

        if (StringUtils.isBlank(schema)) {
            xmlElement.addAttribute(new Attribute("schema", schema)); //$NON-NLS-1$
        }

        if (StringUtils.isBlank(beanName)) {
            xmlElement.addAttribute(new Attribute(
                    "domainObjectName", beanName)); //$NON-NLS-1$
        }


        if (enableComments) {
            xmlElement.addAttribute(new Attribute("enableComments", "true")); //$NON-NLS-1$ //$NON-NLS-2$
        }

        if (ignoredColumns.size() > 0) {
            
        }
        
        return xmlElement;
    }

    /**
     * Validate.
     *
     * @param errors
     *            the errors
     * @param listPosition
     *            the list position
     */
    public void validate(List<String> errors, int listPosition) {

    }

	public boolean isEnableComments() {
		return enableComments;
	}

	public void setEnableComments(boolean enableComments) {
		this.enableComments = enableComments;
	}

	public List<String> getIgnoredColumns() {
		return ignoredColumns;
	}

	public void setIgnoredColumns(List<String> ignoredColumns) {
		this.ignoredColumns = ignoredColumns;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}
	
	public void addIgnoredColumn(String column) {
		ignoredColumns.add(column);
	}
	
	@Override
	public String toString() {
		return "{enableComments=" + enableComments + ", ignoredColumns=" + ignoredColumns
				+ ", schema=" + schema + ", tableName=" + tableName + ", beanName=" + beanName + "}";
	}
}
