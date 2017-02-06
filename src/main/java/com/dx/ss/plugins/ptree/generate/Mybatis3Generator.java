package com.dx.ss.plugins.ptree.generate;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dx.ss.plugins.ptree.config.BaseConfiguration;
import com.dx.ss.plugins.ptree.config.JDBCConnectionConfiguration;
import com.dx.ss.plugins.ptree.db.ConnectionFactory;
import com.dx.ss.plugins.ptree.db.JDBCConnectionFactory;
import com.dx.ss.plugins.ptree.generate.xml.GeneratedXmlFile;

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
        Connection connection = null;
        try {
			connection = getConnection(configuration.getJdbcConnectionConfiguration());
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
