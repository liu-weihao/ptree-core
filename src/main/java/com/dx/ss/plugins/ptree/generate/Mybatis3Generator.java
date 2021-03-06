package com.dx.ss.plugins.ptree.generate;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.dx.ss.plugins.ptree.config.BaseConfiguration;
import com.dx.ss.plugins.ptree.config.JDBCConnectionConfiguration;
import com.dx.ss.plugins.ptree.config.Mybatis3Configuration;
import com.dx.ss.plugins.ptree.config.PackageConfiguration;
import com.dx.ss.plugins.ptree.config.TableConfiguration;
import com.dx.ss.plugins.ptree.config.XmlMapper;
import com.dx.ss.plugins.ptree.config.xml.Attribute;
import com.dx.ss.plugins.ptree.config.xml.DefaultXmlFormatter;
import com.dx.ss.plugins.ptree.config.xml.Document;
import com.dx.ss.plugins.ptree.config.xml.TextElement;
import com.dx.ss.plugins.ptree.config.xml.XmlConstants;
import com.dx.ss.plugins.ptree.config.xml.XmlElement;
import com.dx.ss.plugins.ptree.db.ConnectionFactory;
import com.dx.ss.plugins.ptree.db.JDBCConnectionFactory;
import com.dx.ss.plugins.ptree.generate.java.FullyQualifiedJavaType;
import com.dx.ss.plugins.ptree.generate.java.GeneratedJavaFile;
import com.dx.ss.plugins.ptree.generate.java.JavaAttribute;
import com.dx.ss.plugins.ptree.generate.java.JavaVisibility;
import com.dx.ss.plugins.ptree.generate.java.Method;
import com.dx.ss.plugins.ptree.generate.java.Parameter;
import com.dx.ss.plugins.ptree.generate.java.TreeAttribute;
import com.dx.ss.plugins.ptree.generate.xml.GeneratedXmlFile;
import com.dx.ss.plugins.ptree.impl.DefaultShellCallback;
import com.dx.ss.plugins.ptree.internal.ObjectFactory;
import com.dx.ss.plugins.ptree.internal.ShellCallback;
import com.dx.ss.plugins.ptree.internal.table.IntrospectedColumn;
import com.dx.ss.plugins.ptree.internal.table.IntrospectedTable;
import com.dx.ss.plugins.ptree.utils.ClassloaderUtility;
import com.dx.ss.plugins.ptree.utils.ColumnPropertyUtil;
import com.dx.ss.plugins.ptree.utils.OutputUtil;

public class Mybatis3Generator {

	private ShellCallback shellCallback;
	
	/** The configuration. */
    private BaseConfiguration configuration;
    
    /** The generated java files. */
    private List<GeneratedJavaFile> generatedJavaFiles;

    /** The generated xml files. */
    private List<GeneratedXmlFile> generatedXmlFiles;

	public Mybatis3Generator(ShellCallback shellCallback, BaseConfiguration configuration) {
		super();
		if(shellCallback != null){
			this.shellCallback = shellCallback;
		}else{
			this.shellCallback = new DefaultShellCallback();
		}
		this.configuration = configuration;
		generatedJavaFiles = new ArrayList<GeneratedJavaFile>();
        generatedXmlFiles = new ArrayList<GeneratedXmlFile>();
	}
    
	public void generate(){
		generatedJavaFiles.clear();
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
			generateJavaFiles(introspectTables);
			writeJavaFiles();
			generateXmlFiles(introspectTables);
			writeXmlFiles();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
            closeConnection(connection);
        }
	}

	private List<IntrospectedTable> introspectTables(DatabaseMetaData metaData) throws SQLException{
		ArrayList<TableConfiguration> tableConfigurations = configuration.getTableConfigurations();
		List<IntrospectedTable> introspectedTables = new ArrayList<>();
		PackageConfiguration packageConfiguration = configuration.getPackageConfiguration();
		String parentPackage = packageConfiguration.getBasePackage() 
				+ "." + packageConfiguration.getBeanSubPackage();
		for (TableConfiguration tc : tableConfigurations) {
			// get all columns.
			ResultSet rs = metaData.getColumns(null, tc.getSchema(), tc.getTableName(), null);
			IntrospectedTable introspectedTable = new IntrospectedTable();
			introspectedTable.setFileName(tc.getMapperName());
			introspectedTable.setFullQualifiedJavaType(parentPackage + "." + tc.getBeanName());
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
				String defaultValue = rs.getString("COLUMN_DEF");
				String remarks = rs.getString("REMARKS");
				boolean isAutoIncrement = rs.getString("IS_AUTOINCREMENT").equals("YES");
				boolean nullable = rs.getString("IS_NULLABLE").equals("YES");
				int jdbcType = rs.getInt("DATA_TYPE");
				IntrospectedColumn column = new IntrospectedColumn();
				column.setActualColumnName(columnName);
				column.setAutoIncrement(isAutoIncrement);
				column.setNullable(nullable);
				column.setJdbcType(jdbcType);
				column.setJdbcTypeName(XmlConstants.typeMap.get(jdbcType)[0].toString());
				FullyQualifiedJavaType javaProperty = (FullyQualifiedJavaType) XmlConstants.typeMap.get(jdbcType)[1];
				column.setJavaProperty(javaProperty);
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


	private void generateJavaFiles(List<IntrospectedTable> introspectTables) {
		// TODO Auto-generated method stub
		for(IntrospectedTable it:introspectTables){
			GeneratedJavaFile mapperFile = generateMapper(it);
			generatedJavaFiles.add(mapperFile);
			
			GeneratedJavaFile beanFile = generateBean(it);
			generatedJavaFiles.add(beanFile);
			
			GeneratedJavaFile daoFile = generateDao(it);
			generatedJavaFiles.add(daoFile);
			
			GeneratedJavaFile serviceFile = generateService(it);
			generatedJavaFiles.add(serviceFile);
		}
	}

	private GeneratedJavaFile generateBean(IntrospectedTable introspectedTable) {
		// TODO Auto-generated method stub
		Mybatis3Configuration mybatisConfig = (Mybatis3Configuration) configuration;
		PackageConfiguration packageConfig = mybatisConfig.getPackageConfiguration();
		TableConfiguration table = introspectedTable.getTable();
		StringBuilder importBuilder = new StringBuilder();
		OutputUtil.newLine(importBuilder);
		String beanPackage = packageConfig.getBasePackage() + "." + packageConfig.getBeanSubPackage();
		String targetPackage = packageConfig.getBasePackage() + "." +  packageConfig.getBeanSubPackage();
		GeneratedJavaFile javaFile = new GeneratedJavaFile("src/main/java", targetPackage, table.getBeanName());
		boolean enableComments = introspectedTable.getTable().isEnableComments();
		javaFile.setNeedComments(enableComments);
		javaFile.setPackageName(beanPackage);
		javaFile.setInterface(false);
		javaFile.setClassName(table.getBeanName());
		//extends com.dx.ss.plugins.ptree.model.TreeNode
		FullyQualifiedJavaType superClass = new FullyQualifiedJavaType("com.dx.ss.plugins.ptree.model", "TreeNode", false);
		javaFile.setSuperClass(superClass);
		importBuilder.append("import com.dx.ss.plugins.ptree.model.TreeNode;");
		javaFile.appendImports(importBuilder.toString());
		List<IntrospectedColumn> allColumns = introspectedTable.getAllColumns();
		for(IntrospectedColumn column:allColumns){
			String propertyName = ColumnPropertyUtil.getPropertyFromColumn(column.getActualColumnName());
			if(TreeAttribute.getEnumByAttribute(propertyName) != null)	continue;
			JavaAttribute javaAttribute = new JavaAttribute();
			FullyQualifiedJavaType javaProperty = column.getJavaProperty();
			javaAttribute.setType(javaProperty);
			javaAttribute.setAttributeName(propertyName);
			javaFile.addJavaAttribute(javaAttribute);
			Method getterMethod = new Method("get" + ColumnPropertyUtil.upperFirstLetter(propertyName));
			getterMethod.setReturnType(javaProperty);
			getterMethod.addBodyLine("return this."+propertyName+";");
			javaFile.addMethod(getterMethod);
			Method setterMethod = new Method("set" + ColumnPropertyUtil.upperFirstLetter(propertyName));
			setterMethod.addParameter(new Parameter(javaProperty, propertyName));
			setterMethod.addBodyLine("this."+propertyName+" = "+propertyName+";");
			javaFile.addMethod(setterMethod);
			if(enableComments){
				javaAttribute.setComments(column.getRemarks());
			}
		}
		return javaFile;
	}

	private GeneratedJavaFile generateMapper(IntrospectedTable introspectedTable) {
		// TODO Auto-generated method stub
		Mybatis3Configuration mybatisConfig = (Mybatis3Configuration) configuration;
		XmlMapper packageConfig = mybatisConfig.getXmlMapper();
		TableConfiguration table = introspectedTable.getTable();
		StringBuilder importBuilder = new StringBuilder();
		importBuilder.append("import ");
		importBuilder.append(List.class.getName());
		importBuilder.append(";");
		OutputUtil.newLine(importBuilder);
		
		importBuilder.append("import ");
		importBuilder.append(introspectedTable.getFullQualifiedJavaType());
		importBuilder.append(";");
		OutputUtil.newLine(importBuilder);

		String targetPackage = packageConfig.getModel();
		GeneratedJavaFile javaFile = new GeneratedJavaFile("src/main/java", targetPackage, introspectedTable.getFileName());
		javaFile.setPackageName(packageConfig.getModel());
		javaFile.setInterface(true);
		javaFile.appendImports(importBuilder.toString());
		javaFile.setVisibility(JavaVisibility.PUBLIC);
		javaFile.setClassName(introspectedTable.getFileName());
		Method selectChildNodes = new Method("selectChildNodes");
		selectChildNodes.setVisibility(JavaVisibility.PUBLIC);
		selectChildNodes.setReturnType(new FullyQualifiedJavaType(List.class.getName(), "List<"+table.getBeanName()+">", false));
		selectChildNodes.addParameter(new Parameter(new FullyQualifiedJavaType(introspectedTable.getFullQualifiedJavaType()), ColumnPropertyUtil.lowerFirstLetter(table.getBeanName())));
		javaFile.addMethod(selectChildNodes);
		
		Method getAllParents = new Method("getAllParents");
		getAllParents.setVisibility(JavaVisibility.PUBLIC);
		getAllParents.setReturnType(new FullyQualifiedJavaType(List.class.getName(), "List<"+table.getBeanName()+">", false));
		getAllParents.addParameter(new Parameter(new FullyQualifiedJavaType(introspectedTable.getFullQualifiedJavaType()), ColumnPropertyUtil.lowerFirstLetter(table.getBeanName())));
		javaFile.addMethod(getAllParents);
		
		Method getParentClassify = new Method("getParentClassify");
		getParentClassify.setVisibility(JavaVisibility.PUBLIC);
		getParentClassify.setReturnType(new FullyQualifiedJavaType(introspectedTable.getFullQualifiedJavaType(), table.getBeanName(), false));
		getParentClassify.addParameter(new Parameter(new FullyQualifiedJavaType(introspectedTable.getFullQualifiedJavaType()), ColumnPropertyUtil.lowerFirstLetter(table.getBeanName())));
		javaFile.addMethod(getParentClassify);
		
		Method allocateLeftId = new Method("allocateLeftId");
		allocateLeftId.setVisibility(JavaVisibility.PUBLIC);
		allocateLeftId.setReturnType(new FullyQualifiedJavaType(Integer.class.getName(), "int", true));
		allocateLeftId.addParameter(new Parameter(new FullyQualifiedJavaType(Integer.class.getName()), "leftId"));
		javaFile.addMethod(allocateLeftId);
		
		Method allocateRightId = new Method("allocateRightId");
		allocateRightId.setVisibility(JavaVisibility.PUBLIC);
		allocateRightId.setReturnType(new FullyQualifiedJavaType(Integer.class.getName(), "int", true));
		allocateRightId.addParameter(new Parameter(new FullyQualifiedJavaType(Integer.class.getName()), "rightId"));
		javaFile.addMethod(allocateRightId);
		
		Method recycleLeftId = new Method("recycleLeftId");
		recycleLeftId.setVisibility(JavaVisibility.PUBLIC);
		recycleLeftId.setReturnType(new FullyQualifiedJavaType(Integer.class.getName(), "int", true));
		recycleLeftId.addParameter(new Parameter(new FullyQualifiedJavaType(Integer.class.getName()), "leftId"));
		javaFile.addMethod(recycleLeftId);

		Method recycleRightId = new Method("recycleRightId");
		recycleRightId.setVisibility(JavaVisibility.PUBLIC);
		recycleRightId.setReturnType(new FullyQualifiedJavaType(Integer.class.getName(), "int", true));
		recycleRightId.addParameter(new Parameter(new FullyQualifiedJavaType(Integer.class.getName()) , "rightId"));
		javaFile.addMethod(recycleRightId);
		
		return javaFile;
	}


	private GeneratedJavaFile generateService(IntrospectedTable introspectedTable) {
		// TODO Auto-generated method stub
		Mybatis3Configuration mybatisConfig = (Mybatis3Configuration) configuration;
		PackageConfiguration packageConfig = mybatisConfig.getPackageConfiguration();
		TableConfiguration table = introspectedTable.getTable();
		StringBuilder importBuilder = new StringBuilder();
		
		importBuilder.append("import ");
		importBuilder.append(packageConfig.getBasePackage() + "." + packageConfig.getDaoSubPackage() + "." + table.getBeanName() + "Dao");
		importBuilder.append(";");
		OutputUtil.newLine(importBuilder);
		
		importBuilder.append("import ");
		importBuilder.append(packageConfig.getBasePackage() + "." + packageConfig.getBeanSubPackage() + "." + table.getBeanName());
		importBuilder.append(";");
		OutputUtil.newLine(importBuilder);
		
		importBuilder.append("import com.dx.ss.plugins.ptree.impl.ClassifyService;");

		String fileName = table.getBeanName() + "Service";
		String targetPackage = packageConfig.getBasePackage() + "." + packageConfig.getServiceSubPackage();
		GeneratedJavaFile javaFile = new GeneratedJavaFile("src/main/java", targetPackage, fileName);
		javaFile.appendImports(importBuilder.toString());
		javaFile.setPackageName(targetPackage);
		String simpleName = "ClassifyService<"+table.getBeanName()+", "+table.getBeanName()+"Dao>";
		FullyQualifiedJavaType superClass = new FullyQualifiedJavaType("com.dx.ss.plugins.ptree.impl", simpleName, false);
		javaFile.setSuperClass(superClass);
		javaFile.addClassAnnotions("Service", new FullyQualifiedJavaType("org.springframework.stereotype.Service", "Service", false));
		javaFile.addClassAnnotions("Transactional", new FullyQualifiedJavaType("javax.transaction.Transactional", "Transactional", false));
		javaFile.setClassName(fileName);
		return javaFile;
	}
	

	private GeneratedJavaFile generateDao(IntrospectedTable introspectedTable) {
		// TODO Auto-generated method stub
		Mybatis3Configuration mybatisConfig = (Mybatis3Configuration) configuration;
		PackageConfiguration packageConfig = mybatisConfig.getPackageConfiguration();
		TableConfiguration table = introspectedTable.getTable();
		StringBuilder importBuilder = new StringBuilder();
		importBuilder.append("import ");
		importBuilder.append(List.class.getName());
		importBuilder.append(";");
		OutputUtil.newLine(importBuilder);
		
		importBuilder.append("import ");
		importBuilder.append(packageConfig.getBasePackage() + "." + packageConfig.getBeanSubPackage() + "." + table.getBeanName());
		importBuilder.append(";");
		OutputUtil.newLine(importBuilder);

		importBuilder.append("import com.dx.ss.plugins.ptree.dao.TreeDao;");
		
		String fileName = table.getBeanName() + "Dao";
		String targetPackage = packageConfig.getBasePackage() + "." + packageConfig.getDaoSubPackage();
		GeneratedJavaFile javaFile = new GeneratedJavaFile("src/main/java", targetPackage, fileName);
		javaFile.appendImports(importBuilder.toString());
		javaFile.setPackageName(targetPackage);
		String simpleName = "TreeDao<"+table.getBeanName()+">";
		FullyQualifiedJavaType superClass = new FullyQualifiedJavaType("com.dx.ss.plugins.ptree.dao", simpleName, false);
		javaFile.setSuperClass(superClass);
		javaFile.addClassAnnotions("Repository", new FullyQualifiedJavaType("org.springframework.stereotype.Repository", "Repository", false));
		javaFile.setClassName(fileName);
		return javaFile;
	}
	
	private void writeJavaFiles() {
		// TODO Auto-generated method stub
		if(generatedJavaFiles.size() == 0)	return;
		for(GeneratedJavaFile javaFile:generatedJavaFiles){
			BufferedWriter bw = null;
			try {
				if(javaFile != null){
					javaFile.calculateImports();
					String javaSource = javaFile.getFormattedContent();
					String directory = shellCallback.getDirectory(javaFile.getTargetProject(), javaFile.getTargetPackage()).getAbsolutePath();
					String filePath =  directory + File.separatorChar + javaFile.getFileName() + ".java";
					FileOutputStream fos = new FileOutputStream(new File(filePath), false);
					OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
					bw = new BufferedWriter(osw);
					bw.write(javaSource);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				try {
					if(bw != null)	bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void generateXmlFiles(List<IntrospectedTable> introspectTables) {
		Mybatis3Configuration mybatisConfig = (Mybatis3Configuration) configuration;
		for(IntrospectedTable it:introspectTables){
			Document document = new Document(
					XmlConstants.MYBATIS3_MAPPER_PUBLIC_ID,
					XmlConstants.MYBATIS3_MAPPER_SYSTEM_ID);
			document.setRootElement(getSqlMapElement(mybatisConfig, it));
			XmlMapper xmlMapper = mybatisConfig.getXmlMapper();
			String targetProject = xmlMapper.getSqlmap();
			GeneratedXmlFile xml = new GeneratedXmlFile(document, it.getFileName(), null, targetProject, new DefaultXmlFormatter());
			generatedXmlFiles.add(xml);
		}
	}
	
	private XmlElement getSqlMapElement(Mybatis3Configuration mybatisConfig, IntrospectedTable introspectedTable) {
		TableConfiguration table = introspectedTable.getTable();
		XmlElement answer = new XmlElement("mapper");
		answer.addAttribute(new Attribute("namespace", mybatisConfig.getXmlMapper().getModel()+"."+introspectedTable.getFileName()));
		/***************************<resultMap>****************************/
		XmlElement resultMap = new XmlElement("resultMap");
		resultMap.addAttribute(new Attribute("id", "BaseResultMap"));
		resultMap.addAttribute(new Attribute("type", introspectedTable.getFullQualifiedJavaType()));
		XmlElement idElement = new XmlElement("id");
		IntrospectedColumn primaryKeyColumn = introspectedTable.getPrimaryKeyColumn();
		idElement.addAttribute(new Attribute("column", primaryKeyColumn.getActualColumnName()));
		idElement.addAttribute(new Attribute("property", ColumnPropertyUtil.getPropertyFromColumn(primaryKeyColumn.getActualColumnName())));
		idElement.addAttribute(new Attribute("jdbcType", primaryKeyColumn.getJdbcTypeName()));
		resultMap.addElement(idElement);
		List<IntrospectedColumn> baseColumns = introspectedTable.getBaseColumns();
		for(IntrospectedColumn baseColumn: baseColumns){
			XmlElement ele = new XmlElement("result");
			ele.addAttribute(new Attribute("column", baseColumn.getActualColumnName()));
			ele.addAttribute(new Attribute("property", ColumnPropertyUtil.getPropertyFromColumn(baseColumn.getActualColumnName())));
			ele.addAttribute(new Attribute("jdbcType", baseColumn.getJdbcTypeName()));
			resultMap.addElement(ele);
		}
		answer.addElement(resultMap);
		/***************************<resultMap>****************************/
		
		/***************************<select id="selectChildNodes">****************************/
		XmlElement selectChildNodes = new XmlElement("select");
		selectChildNodes.addAttribute(new Attribute("id", "selectChildNodes"));
		selectChildNodes.addAttribute(new Attribute("parameterType", introspectedTable.getFullQualifiedJavaType()));
		selectChildNodes.addAttribute(new Attribute("resultMap", "BaseResultMap"));
		String selectChildNodesSql = "<![CDATA[ SELECT * FROM "+table.getTableName()+" WHERE left_id>#{leftId} AND right_id<#{rightId} AND nlevel=#{level} + 1 ORDER BY left_id ASC ]]>";
		selectChildNodes.addElement(new TextElement(selectChildNodesSql));
		answer.addElement(selectChildNodes);
		/***************************<select id="selectChildNodes">****************************/
		
		/***************************<select id="getAllParents">****************************/
		XmlElement getAllParents = new XmlElement("select");
		getAllParents.addAttribute(new Attribute("id", "getAllParents"));
		getAllParents.addAttribute(new Attribute("parameterType", introspectedTable.getFullQualifiedJavaType()));
		getAllParents.addAttribute(new Attribute("resultMap", "BaseResultMap"));
		String getAllParentsSql = "<![CDATA[ SELECT * FROM "+table.getTableName()+" WHERE left_id<=#{leftId} AND right_id>=#{rightId} AND nlevel>0 ORDER BY left_id ASC ]]>";
		getAllParents.addElement(new TextElement(getAllParentsSql));
		answer.addElement(getAllParents);
		/***************************<select id="getAllParents">****************************/
		
		/***************************<select id="getParentClassify">****************************/
		XmlElement getParentClassify = new XmlElement("select");
		getParentClassify.addAttribute(new Attribute("id", "getParentClassify"));
		getParentClassify.addAttribute(new Attribute("parameterType", introspectedTable.getFullQualifiedJavaType()));
		getParentClassify.addAttribute(new Attribute("resultMap", "BaseResultMap"));
		String getParentClassifySql = "<![CDATA[ SELECT * FROM "+table.getTableName()+" WHERE left_id<#{leftId} AND right_id>#{rightId} AND nlevel=#{nlevel} - 1 ]]>";
		getParentClassify.addElement(new TextElement(getParentClassifySql));
		answer.addElement(getParentClassify);
		/***************************<select id="getParentClassify">****************************/
		
		/***************************<update id="allocateLeftId">****************************/
		XmlElement allocateLeftId = new XmlElement("update");
		allocateLeftId.addAttribute(new Attribute("id", "allocateLeftId"));
		allocateLeftId.addAttribute(new Attribute("parameterType", "map"));
		String allocateLeftIdSql = "<![CDATA[ UPDATE "+table.getTableName()+" SET left_id = left_id + 2  WHERE left_id > #{leftId} ]]>";
		allocateLeftId.addElement(new TextElement(allocateLeftIdSql));
		answer.addElement(allocateLeftId);
		/***************************<update id="allocateLeftId">****************************/
		
		/***************************<update id="allocateRightId">****************************/
		XmlElement allocateRightId = new XmlElement("update");
		allocateRightId.addAttribute(new Attribute("id", "allocateRightId"));
		allocateRightId.addAttribute(new Attribute("parameterType", "map"));
		String allocateRightIdSql = "<![CDATA[ UPDATE "+table.getTableName()+" SET right_id = right_id + 2  WHERE right_id > #{rightId} ]]>";
		allocateRightId.addElement(new TextElement(allocateRightIdSql));
		answer.addElement(allocateRightId);
		/***************************<update id="allocateRightId">****************************/
		
		/***************************<update id="recycleLeftId">****************************/
		XmlElement recycleLeftId = new XmlElement("update");
		recycleLeftId.addAttribute(new Attribute("id", "recycleLeftId"));
		recycleLeftId.addAttribute(new Attribute("parameterType", "map"));
		String recycleLeftIdSql = "<![CDATA[ UPDATE "+table.getTableName()+" SET right_id = right_id + 2  WHERE right_id > #{rightId} ]]>";
		recycleLeftId.addElement(new TextElement(recycleLeftIdSql));
		answer.addElement(recycleLeftId);
		/***************************<update id="recycleLeftId">****************************/
		
		/***************************<update id="recycleRightId">****************************/
		XmlElement recycleRightId = new XmlElement("update");
		recycleRightId.addAttribute(new Attribute("id", "recycleRightId"));
		recycleRightId.addAttribute(new Attribute("parameterType", "map"));
		String recycleRightIdSql = "<![CDATA[ UPDATE "+table.getTableName()+" SET right_id=right_id - 2 WHERE right_id > #{rightId} ]]>";
		recycleRightId.addElement(new TextElement(recycleRightIdSql));
		answer.addElement(recycleRightId);
		/***************************<update id="recycleRightId">****************************/
		return answer;
	}
	

	private void writeXmlFiles() {
		if(generatedXmlFiles.size() == 0)	return;
		for(GeneratedXmlFile xmlFile:generatedXmlFiles){
			BufferedWriter bw = null;
			try {
				String xmlContent = xmlFile.getFormattedContent();
				String directory = shellCallback.getDirectory(xmlFile.getTargetProject(), xmlFile.getTargetPackage()).getAbsolutePath();
				String filePath =  directory + File.separatorChar + xmlFile.getFileName() + ".xml";
				FileOutputStream fos = new FileOutputStream(new File(filePath), false);
				OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
				bw = new BufferedWriter(osw);
				bw.write(xmlContent);
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				try {
					if(bw != null)	bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
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
