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
package com.dx.ss.plugins.ptree.internal.table;

import java.sql.Types;

import com.dx.ss.plugins.ptree.generate.java.FullyQualifiedJavaType;

/**
 * This class holds information about an introspected column. The class has
 * utility methods useful for generating iBATIS objects.
 * 
 * @author Jeff Butler
 */
public class IntrospectedColumn {
	
	private String actualColumnName;

    private int jdbcType;

    private String jdbcTypeName;

    private boolean nullable;

    // any database comment associated with this column. May be null
    private String remarks;

    private String defaultValue;
    
    private FullyQualifiedJavaType javaProperty;
    
    /**
     * true if the JDBC driver reports that this column is auto-increment
     */
    private boolean isAutoIncrement;
    
    public IntrospectedColumn() {
        super();
    }

    public int getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(int jdbcType) {
        this.jdbcType = jdbcType;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    @Override
	public boolean equals(Object obj) {
    	if(obj instanceof IntrospectedColumn){
    		IntrospectedColumn o = (IntrospectedColumn) obj;
    		return this.actualColumnName.equals(o.getActualColumnName());
    	}
    	return false;
	}

	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Actual Column Name: "); //$NON-NLS-1$
        sb.append(actualColumnName);
        sb.append(", JDBC Type: "); //$NON-NLS-1$
        sb.append(jdbcType);
        sb.append(", Nullable: "); //$NON-NLS-1$
        sb.append(nullable);
        return sb.toString();
    }

    public void setActualColumnName(String actualColumnName) {
        this.actualColumnName = actualColumnName;
    }

    public boolean isBLOBColumn() {
        String typeName = getJdbcTypeName();

        return "BINARY".equals(typeName) || "BLOB".equals(typeName) //$NON-NLS-1$ //$NON-NLS-2$
                || "CLOB".equals(typeName) || "LONGNVARCHAR".equals(typeName) //$NON-NLS-1$ //$NON-NLS-2$ 
                || "LONGVARBINARY".equals(typeName) || "LONGVARCHAR".equals(typeName) //$NON-NLS-1$ //$NON-NLS-2$
                || "NCLOB".equals(typeName) || "VARBINARY".equals(typeName); //$NON-NLS-1$ //$NON-NLS-2$ 
    }

    public boolean isJdbcCharacterColumn() {
        return jdbcType == Types.CHAR || jdbcType == Types.CLOB
                || jdbcType == Types.LONGVARCHAR || jdbcType == Types.VARCHAR
                || jdbcType == Types.LONGNVARCHAR || jdbcType == Types.NCHAR
                || jdbcType == Types.NCLOB || jdbcType == Types.NVARCHAR;
    }

    public boolean isJDBCDateColumn() {
        return "DATE".equalsIgnoreCase(jdbcTypeName); //$NON-NLS-1$
    }

    public String getActualColumnName() {
        return actualColumnName;
    }

    public String getJdbcTypeName() {
        if (jdbcTypeName == null) {
            return "OTHER"; //$NON-NLS-1$
        }

        return jdbcTypeName;
    }

    public void setJdbcTypeName(String jdbcTypeName) {
        this.jdbcTypeName = jdbcTypeName;
    }


    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public FullyQualifiedJavaType getJavaProperty() {
		return javaProperty;
	}

	public void setJavaProperty(FullyQualifiedJavaType javaProperty) {
		this.javaProperty = javaProperty;
	}

	public boolean isAutoIncrement() {
        return isAutoIncrement;
    }

    public void setAutoIncrement(boolean isAutoIncrement) {
        this.isAutoIncrement = isAutoIncrement;
    }

}
