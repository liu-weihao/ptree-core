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
			ArrayList<TableConfiguration> tableConfigurations = configuration.getTableConfigurations();
			List<IntrospectedTable> introspectedTables = new ArrayList<>();
			for (TableConfiguration tc : tableConfigurations) {
				//get all columns.
				ResultSet rs = metaData.getColumns(null, tc.getSchema(), tc.getTableName(), null);
				IntrospectedTable introspectedTable = new IntrospectedTable();
				introspectedTable.setTable(tc);
				List<IntrospectedColumn> baseColumns = new ArrayList<>();
				while (rs.next()) {
					 IntrospectedColumn column = new IntrospectedColumn();
					 column.setActualColumnName(rs.getString("COLUMN_NAME"));
					 column.setAutoIncrement(rs.getString("IS_AUTOINCREMENT").equals("YES"));
					 column.setNullable(rs.getString("IS_NULLABLE").equals("YES"));
					 column.setJdbcTypeName(rs.getString("TYPE_NAME"));
					 column.setJdbcType(rs.getInt("DATA_TYPE"));
					 column.setDefaultValue(rs.getString("COLUMN_DEF"));
					 column.setRemarks(rs.getString("REMARKS"));
					 baseColumns.add(column);
				 }
				 ResultSet primaryKeys = metaData.getPrimaryKeys(null, tc.getSchema(), tc.getTableName());
				 while (primaryKeys.next()) {
					IntrospectedColumn primaryKeyColumn = new IntrospectedColumn();
					introspectedTable.setPrimaryKeyColumn(primaryKeyColumn );
				 }
				 introspectedTable.setBaseColumns(baseColumns);
				 introspectedTables.add(introspectedTable);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
            closeConnection(connection);
        }
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
