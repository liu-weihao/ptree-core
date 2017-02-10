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
package com.dx.ss.plugins.ptree.config.xml;

import java.awt.List;
import java.math.BigDecimal;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.dx.ss.plugins.ptree.generate.java.FullyQualifiedJavaType;

public class XmlConstants {

	public static Map<Integer, Object[]> typeMap;
	
    /**
     * Utility Class, no instances
     */
    private XmlConstants() {
        super();
    }

    public static final String IBATIS2_SQL_MAP_SYSTEM_ID = "http://ibatis.apache.org/dtd/sql-map-2.dtd"; //$NON-NLS-1$

    public static final String IBATIS2_SQL_MAP_PUBLIC_ID = "-//ibatis.apache.org//DTD SQL Map 2.0//EN"; //$NON-NLS-1$

    public static final String IBATIS2_SQL_MAP_CONFIG_SYSTEM_ID = "http://ibatis.apache.org/dtd/sql-map-config-2.dtd"; //$NON-NLS-1$

    public static final String IBATIS2_SQL_MAP_CONFIG_PUBLIC_ID = "-//ibatis.apache.org//DTD SQL Map Config 2.0//EN"; //$NON-NLS-1$

    public static final String MYBATIS3_MAPPER_SYSTEM_ID = "http://mybatis.org/dtd/mybatis-3-mapper.dtd"; //$NON-NLS-1$

    public static final String MYBATIS3_MAPPER_PUBLIC_ID = "-//mybatis.org//DTD Mapper 3.0//EN"; //$NON-NLS-1$

    public static final String MYBATIS3_MAPPER_CONFIG_SYSTEM_ID = "http://mybatis.org/dtd/mybatis-3-config.dtd"; //$NON-NLS-1$

    public static final String MYBATIS3_MAPPER_CONFIG_PUBLIC_ID = "-//mybatis.org//DTD Config 3.0//EN"; //$NON-NLS-1$

    public static final String IBATOR_CONFIG_SYSTEM_ID = "http://ibatis.apache.org/dtd/ibator-config_1_0.dtd"; //$NON-NLS-1$

    public static final String IBATOR_CONFIG_PUBLIC_ID = "-//Apache Software Foundation//DTD Apache iBATIS Ibator Configuration 1.0//EN"; //$NON-NLS-1$

    public static final String MYBATIS_GENERATOR_CONFIG_SYSTEM_ID = "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd"; //$NON-NLS-1$

    public static final String MYBATIS_GENERATOR_CONFIG_PUBLIC_ID = "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"; //$NON-NLS-1$
    
    static {
    	typeMap = new HashMap<Integer, Object[]>();
        typeMap.put(Types.ARRAY, new Object[]{"ARRAY", new FullyQualifiedJavaType(List.class.getName(), "List<?>", false)});
        typeMap.put(Types.BIGINT, new Object[]{"BIGINT", new FullyQualifiedJavaType(Long.class.getName(), "Long", false)});
        typeMap.put(Types.BINARY, new Object[]{"BINARY", new FullyQualifiedJavaType(Byte.class.getName(), "Byte[]", false)});
        typeMap.put(Types.BIT, new Object[]{"BIT", new FullyQualifiedJavaType(Boolean.class.getName(), "Boolean", false)});
        typeMap.put(Types.BLOB, new Object[]{"BLOB", new FullyQualifiedJavaType(String.class.getName(), "String", false)});
        typeMap.put(Types.BOOLEAN, new Object[]{"BOOLEAN", new FullyQualifiedJavaType(Boolean.class.getName(), "Boolean", false)});
        typeMap.put(Types.CHAR, new Object[]{"CHAR", new FullyQualifiedJavaType(null, "char", true)});
        typeMap.put(Types.CLOB, new Object[]{"CLOB", new FullyQualifiedJavaType(Byte.class.getName(), "Byte[]", false)});
        typeMap.put(Types.DATALINK, new Object[]{"DATALINK", new FullyQualifiedJavaType(String.class.getName(), "String", false)});
        typeMap.put(Types.DATE, new Object[]{"DATE", new FullyQualifiedJavaType(Date.class.getName(), "Date", false) });
        typeMap.put(Types.DECIMAL, new Object[]{"DECIMAL", new FullyQualifiedJavaType(BigDecimal.class.getName(), "BigDecimal", false) });
        typeMap.put(Types.DISTINCT, new Object[]{"DISTINCT", new FullyQualifiedJavaType(String.class.getName(), "String", false)});
        typeMap.put(Types.DOUBLE, new Object[]{"DOUBLE", new FullyQualifiedJavaType(Double.class.getName(), "Double", false)});
        typeMap.put(Types.FLOAT, new Object[]{"FLOAT", new FullyQualifiedJavaType(Float.class.getName(), "Float", false)});
        typeMap.put(Types.INTEGER, new Object[]{"INTEGER", new FullyQualifiedJavaType(Integer.class.getName(), "Integer", false)});
        typeMap.put(Types.JAVA_OBJECT, new Object[]{"JAVA_OBJECT", new FullyQualifiedJavaType(Object.class.getName(), "Object", false)});
        typeMap.put(Types.LONGNVARCHAR, new Object[]{ "LONGNVARCHAR", new FullyQualifiedJavaType(String.class.getName(), "String", false) });
        typeMap.put(Types.LONGVARBINARY, new Object[]{ "LONGVARBINARY", new FullyQualifiedJavaType(Byte.class.getName(), "Byte[]", false) });
        typeMap.put(Types.LONGVARCHAR, new Object[]{ "LONGVARCHAR", new FullyQualifiedJavaType(String.class.getName(), "String", false) });
        typeMap.put(Types.NCHAR, new Object[]{ "NCHAR", new FullyQualifiedJavaType(String.class.getName(), "String", false) });
        typeMap.put(Types.NCLOB, new Object[]{ "NCLOB", new FullyQualifiedJavaType(Byte.class.getName(), "Byte[]", false) });
        typeMap.put(Types.NVARCHAR, new Object[]{"NVARCHAR", new FullyQualifiedJavaType(String.class.getName(), "String", false)});
        typeMap.put(Types.NULL, new Object[]{ "NULL", new FullyQualifiedJavaType(Object.class.getName(), "Object", false) });
        typeMap.put(Types.NUMERIC, new Object[]{"NUMERIC", new FullyQualifiedJavaType(Number.class.getName(), "Number", false) });
        typeMap.put(Types.OTHER, new Object[]{"OTHER", new FullyQualifiedJavaType(Object.class.getName(), "Object", false) });
        typeMap.put(Types.REAL, new Object[]{"REAL", new FullyQualifiedJavaType(Number.class.getName(), "Number", false) });
        typeMap.put(Types.REF, new Object[]{"REF", new FullyQualifiedJavaType(Object.class.getName(), "Object", false) });
        typeMap.put(Types.SMALLINT, new Object[]{"SMALLINT", new FullyQualifiedJavaType(Short.class.getName(), "Short", false) });
        typeMap.put(Types.STRUCT, new Object[]{"STRUCT", new FullyQualifiedJavaType(Object.class.getName(), "Object", false) });
        typeMap.put(Types.TIME, new Object[]{"TIME", new FullyQualifiedJavaType(Date.class.getName(), "Date", false) });
        typeMap.put(Types.TIMESTAMP, new Object[]{"TIMESTAMP", new FullyQualifiedJavaType(Date.class.getName(), "Date", false) });
        typeMap.put(Types.TINYINT, new Object[]{"TINYINT", new FullyQualifiedJavaType(Short.class.getName(), "Short", false) });
        typeMap.put(Types.VARBINARY, new Object[]{"VARBINARY", new FullyQualifiedJavaType(Byte.class.getName(), "Byte[]", false) });
        typeMap.put(Types.VARCHAR, new Object[]{"VARCHAR", new FullyQualifiedJavaType(String.class.getName(), "String", false)});
    }
}
