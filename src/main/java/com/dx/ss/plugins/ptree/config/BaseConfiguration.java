package com.dx.ss.plugins.ptree.config;

import java.util.ArrayList;

/**
 * All xml config files are based this class.
 * You can add some extra configuration items by extends this class.
 * @author Frank
 */
public abstract class BaseConfiguration {

	 /** The class path entry. */
	private String classPathEntry;
	
	 /** The jdbc connection configuration. */
	private JDBCConnectionConfiguration jdbcConnectionConfiguration;
	
	/** The table configurations. */
    private ArrayList<TableConfiguration> tableConfigurations = new ArrayList<>();

    /** The package configurations. */
    private PackageConfiguration packageConfiguration;
    
	public String getClassPathEntry() {
		return classPathEntry;
	}

	public void setClassPathEntry(String classPathEntry) {
		this.classPathEntry = classPathEntry;
	}

	public JDBCConnectionConfiguration getJdbcConnectionConfiguration() {
		return jdbcConnectionConfiguration;
	}

	public void setJdbcConnectionConfiguration(JDBCConnectionConfiguration jdbcConnectionConfiguration) {
		this.jdbcConnectionConfiguration = jdbcConnectionConfiguration;
	}

	public ArrayList<TableConfiguration> getTableConfigurations() {
		return tableConfigurations;
	}

	public void setTableConfigurations(ArrayList<TableConfiguration> tableConfigurations) {
		this.tableConfigurations = tableConfigurations;
	}

	public PackageConfiguration getPackageConfiguration() {
		return packageConfiguration;
	}

	public void setPackageConfiguration(PackageConfiguration packageConfiguration) {
		this.packageConfiguration = packageConfiguration;
	}

	public void addTableConfiguration(TableConfiguration tc) {
		tableConfigurations.add(tc);
	}
	
	@Override
	public String toString() {
		return "classPathEntry=" + classPathEntry + ", jdbc="
				+ jdbcConnectionConfiguration + ", table=" + tableConfigurations
				+ ", package=" + packageConfiguration;
	}
	
}
