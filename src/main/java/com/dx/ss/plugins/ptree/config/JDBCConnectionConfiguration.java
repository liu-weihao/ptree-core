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

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.dx.ss.plugins.ptree.config.xml.Attribute;
import com.dx.ss.plugins.ptree.config.xml.XmlElement;

public class JDBCConnectionConfiguration {

    private String driverClass;

    private String connectionURL;

    private String user;

    private String password;

    public JDBCConnectionConfiguration() {
        super();
    }

    public String getConnectionURL() {
        return connectionURL;
    }

    public void setConnectionURL(String connectionURL) {
        this.connectionURL = connectionURL;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public XmlElement toXmlElement() {
        XmlElement xmlElement = new XmlElement("jdbcConnection"); //$NON-NLS-1$
        xmlElement.addAttribute(new Attribute("driverClass", driverClass)); //$NON-NLS-1$
        xmlElement.addAttribute(new Attribute("connectionURL", connectionURL)); //$NON-NLS-1$

        if (StringUtils.isBlank(user)) {
            xmlElement.addAttribute(new Attribute("userId", user)); //$NON-NLS-1$
        }

        if (StringUtils.isBlank(password)) {
            xmlElement.addAttribute(new Attribute("password", password)); //$NON-NLS-1$
        }

        return xmlElement;
    }

    public void validate(List<String> errors) {
        if (!StringUtils.isBlank(driverClass)) {
            
        }

        if (!StringUtils.isBlank(connectionURL)) {
            
        }
    }

	@Override
	public String toString() {
		return "[driverClass=" + driverClass + ", connectionURL=" + connectionURL
				+ ", userId=" + user + ", password=" + password + "]";
	}
    
}
