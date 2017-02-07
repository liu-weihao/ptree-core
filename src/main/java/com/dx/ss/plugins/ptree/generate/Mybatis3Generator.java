package com.dx.ss.plugins.ptree.generate;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.dx.ss.plugins.ptree.config.BaseConfiguration;
import com.dx.ss.plugins.ptree.config.JDBCConnectionConfiguration;
import com.dx.ss.plugins.ptree.config.TableConfiguration;
import com.dx.ss.plugins.ptree.config.xml.DefaultXmlFormatter;
import com.dx.ss.plugins.ptree.config.xml.Document;
import com.dx.ss.plugins.ptree.config.xml.XmlConstants;
import com.dx.ss.plugins.ptree.config.xml.XmlElement;
import com.dx.ss.plugins.ptree.db.ConnectionFactory;
import com.dx.ss.plugins.ptree.db.JDBCConnectionFactory;
import com.dx.ss.plugins.ptree.generate.xml.GeneratedXmlFile;
import com.dx.ss.plugins.ptree.internal.ObjectFactory;
import com.dx.ss.plugins.ptree.internal.table.IntrospectedColumn;
import com.dx.ss.plugins.ptree.internal.table.IntrospectedTable;
import com.dx.ss.plugins.ptree.utils.ClassloaderUtility;

public class Mybatis3Generator {

	/** The configuration. */
    private BaseConfiguration configuration;
    
    /** The generated java files. */
//    private List<GeneratedJavaFile> generatedJavaFiles;

    /** The generated xml files. */
    private List<GeneratedXmlFile> generatedXmlFiles;

	public Mybatis3Generator(BaseConfiguration configuration) {
		super();
		this.configuration = configuration;
//		generatedJavaFiles = new ArrayList<GeneratedJavaFile>();
        generatedXmlFiles = new ArrayList<GeneratedXmlFile>();
	}
    
	public void generate(){
//		generatedJavaFiles.clear();
        generatedXmlFiles.clear();
        ObjectFactory.reset();
        String classPathEntry = configuration.getClassPathEntry();
        if(StringUtils.isNotBlank(classPathEntry)){
        	ObjectFactory.addExternalClassLoader(ClassloaderUtility.getCustomClassloader(classPathEntry));
        }
        Connection connection = null;
		try {
			connection = getConnection(configuration.getJdbcConnectionConfiguration());
			DatabaseMetaData metaData = connection.getMetaData();
			List<IntrospectedTable> introspectTables = introspectTables(metaData);
			generateXmlFiles(introspectTables);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
            closeConnection(connection);
        }
	}

	protected List<IntrospectedTable> introspectTables(DatabaseMetaData metaData) throws SQLException{
		ArrayList<TableConfiguration> tableConfigurations = configuration.getTableConfigurations();
		List<IntrospectedTable> introspectedTables = new ArrayList<>();
		for (TableConfiguration tc : tableConfigurations) {
			// get all columns.
			ResultSet rs = metaData.getColumns(null, tc.getSchema(), tc.getTableName(), null);
			IntrospectedTable introspectedTable = new IntrospectedTable();
			introspectedTable.setFileName(tc.getMapperName());
			introspectedTable.setTable(tc);
			// get primary key. first one default.
			ResultSet primaryKeys = metaData.getPrimaryKeys(null, tc.getSchema(), tc.getTableName());
			String primaryKey = null;
			if (primaryKeys.next()) {
				primaryKey = primaryKeys.getString("COLUMN_NAME");
			}
			List<String> ignoredColumns = tc.getIgnoredColumns();
			List<IntrospectedColumn> baseColumns = new ArrayList<>();
			while (rs.next()) {
				String columnName = rs.getString("COLUMN_NAME");
				if(ignoredColumns.contains(columnName))	continue;	//ignore this column.
				String typeName = rs.getString("TYPE_NAME");
				String defaultValue = rs.getString("COLUMN_DEF");
				String remarks = rs.getString("REMARKS");
				boolean isAutoIncrement = rs.getString("IS_AUTOINCREMENT").equals("YES");
				boolean nullable = rs.getString("IS_NULLABLE").equals("YES");
				int jdbcType = rs.getInt("DATA_TYPE");
				IntrospectedColumn column = new IntrospectedColumn();
				column.setActualColumnName(columnName);
				column.setAutoIncrement(isAutoIncrement);
				column.setNullable(nullable);
				column.setJdbcTypeName(typeName);
				column.setJdbcType(jdbcType);
				column.setDefaultValue(defaultValue);
				column.setRemarks(remarks);
				if(columnName.equals(primaryKey)){//primary key
					introspectedTable.setPrimaryKeyColumn(column);
				}else{
					baseColumns.add(column);
				}
			}
			introspectedTable.setBaseColumns(baseColumns);
			introspectedTables.add(introspectedTable);
		}
		return introspectedTables;
	}

	private void generateXmlFiles(List<IntrospectedTable> introspectTables) {
		// TODO Auto-generated method stub
		for(IntrospectedTable it:introspectTables){
			Document document = new Document(
					XmlConstants.MYBATIS3_MAPPER_PUBLIC_ID,
					XmlConstants.MYBATIS3_MAPPER_SYSTEM_ID);
			document.setRootElement(getSqlMapElement(it));
			GeneratedXmlFile xml = new GeneratedXmlFile(document, it.getFileName(), "", "", new DefaultXmlFormatter());
			generatedXmlFiles.add(xml);
		}
	}
	
	private XmlElement getSqlMapElement(IntrospectedTable introspectedTable) {
		XmlElement answer = new XmlElement("mapper");
		
		return answer;
	}
	
	/**
     * Gets the connection.
	 * @return the connection
     * @throws SQLException
     *             the SQL exception
     */
    private Connection getConnection(JDBCConnectionConfiguration jdbc) throws SQLException {
        ConnectionFactory connectionFactory = null;
        if (jdbc != null) {
            connectionFactory = new JDBCConnectionFactory(jdbc);
            return connectionFactory.getConnection();
        }
        return null;
    }

    /**
     * Close connection.
     *
     * @param connection
     *            the connection
     */
    private void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }
}
