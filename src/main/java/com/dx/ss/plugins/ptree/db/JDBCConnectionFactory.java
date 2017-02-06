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
package com.dx.ss.plugins.ptree.db;


import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import com.dx.ss.plugins.ptree.config.JDBCConnectionConfiguration;

public class JDBCConnectionFactory implements ConnectionFactory {

    private String userId;
    private String password;
    private String connectionURL;
    private String driverClass;
    /**
     * This constructor is called when there is a JDBCConnectionConfiguration
     * specified in the configuration.
     * 
     * @param config
     */
    public JDBCConnectionFactory(JDBCConnectionConfiguration config) {
        super();
        userId = config.getUser();
        password = config.getPassword();
        connectionURL = config.getConnectionURL();
        driverClass = config.getDriverClass();
    }
    
    /**
     * This constructor is called when this connection factory is specified 
     * as the type in a ConnectionFactory configuration element. 
     */
    public JDBCConnectionFactory() {
        super();
    }

    public Connection getConnection()
            throws SQLException {
        Driver driver = getDriver();

        Properties props = new Properties();

        if (StringUtils.isNotBlank(userId)) {
            props.setProperty("user", userId);
        }

        if (StringUtils.isNotBlank(password)) {
            props.setProperty("password", password); 
        }

        Connection conn = driver.connect(connectionURL, props);

        if (conn == null) {
            throw new SQLException();
        }

        return conn;
    }

    private Driver getDriver() {
        Driver driver = null;
        try {
            Class<?> clazz = Class.forName(driverClass);
            driver = (Driver) clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return driver;
    }

    @Override
    public void addConfigurationProperties(Properties properties) {
        // this should only be called when this connection factory is
        // specified in a ConnectionFactory configuration
        userId = properties.getProperty("userId");
        password = properties.getProperty("password");
        connectionURL = properties.getProperty("connectionURL");
        driverClass = properties.getProperty("driverClass");
    }
}
