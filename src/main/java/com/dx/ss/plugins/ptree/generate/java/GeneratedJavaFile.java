package com.dx.ss.plugins.ptree.generate.java;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.dx.ss.plugins.ptree.generate.GeneratedFile;
import com.dx.ss.plugins.ptree.utils.OutputUtil;

public class GeneratedJavaFile extends GeneratedFile {

	/** The file name. */
    private String fileName;
    
    /** The target package. */
    private String targetPackage;
    
	private String packageName;
	
	private String imports = "";
	
	private JavaVisibility visibility = JavaVisibility.PUBLIC;
	
	private boolean isInterface = false;
	
	private String className;
	
	private Map<String, FullyQualifiedJavaType> classAnnotions;
	
	private List<JavaAttribute> javaAttributes = null;
	
	private List<Method> methods = null;
	
	private FullyQualifiedJavaType superClass;
	
	private boolean needComments;

	public GeneratedJavaFile(String targetProject, String targetPackage, String fileName) {
		super(targetProject);
		this.targetPackage = targetPackage;
		this.fileName = fileName;
		methods = new ArrayList<>();
		javaAttributes = new ArrayList<>();
		classAnnotions = new HashMap<>();
	}
	
	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getImports() {
		return imports;
	}

	public JavaVisibility getVisibility() {
		return visibility;
	}

	public void setVisibility(JavaVisibility visibility) {
		this.visibility = visibility;
	}

	public boolean isInterface() {
		return isInterface;
	}

	public void setInterface(boolean isInterface) {
		this.isInterface = isInterface;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Map<String, FullyQualifiedJavaType> getClassAnnotions() {
		return classAnnotions;
	}

	public void addClassAnnotions(String name, FullyQualifiedJavaType type) {
		this.classAnnotions.put(name, type);
	}

	public List<JavaAttribute> getJavaAttributes() {
		return javaAttributes;
	}

	public void addJavaAttribute(JavaAttribute javaAttribute) {
		this.javaAttributes.add(javaAttribute);
	}

	public List<Method> getMethods() {
		return methods;
	}

	public void addMethod(Method method) {
		if(method != null)		this.methods.add(method);
	}
	
	public FullyQualifiedJavaType getSuperClass() {
		return superClass;
	}

	public void setSuperClass(FullyQualifiedJavaType superClass) {
		this.superClass = superClass;
	}

	public boolean isNeedComments() {
		return needComments;
	}

	public void setNeedComments(boolean needComments) {
		this.needComments = needComments;
	}

	public String getFormattedContent(){
		StringBuilder content = new StringBuilder();
		content.append("package ");
		content.append(packageName);
		content.append(";");
		OutputUtil.newLine(content);
		OutputUtil.newLine(content);
		content.append(StringUtils.isBlank(imports)?StringUtils.EMPTY:imports);
		OutputUtil.newLine(content);
		if(classAnnotions.size() > 0){
			Set<String> keySet = classAnnotions.keySet();
			for(String name:keySet){
				content.append("@");
				content.append(name);
				OutputUtil.newLine(content);
			}
		}
		content.append(visibility.getValue());
		content.append(isInterface ? "interface " : "class ");
		content.append(className);
		if(superClass != null){
			content.append(" extends ");
			content.append(superClass.getSimpleName());
		}
		content.append(" {");
		OutputUtil.newLine(content);
		
		if(javaAttributes != null){
			for(JavaAttribute attr : javaAttributes){
				OutputUtil.newLine(content);
				if(needComments && StringUtils.isNoneBlank(attr.getComments())){
					OutputUtil.javaIndent(content, 1);
					content.append("/**");
					content.append(attr.getComments());
					content.append("*/");
					OutputUtil.newLine(content);
				}
				OutputUtil.javaIndent(content, 1);
				content.append(attr.getVisibility().getValue());
				content.append(attr.getType().getSimpleName());
				content.append(" ");
				content.append(attr.getAttributeName());
				content.append(";");
				OutputUtil.newLine(content);
			}
		}
		
		if(methods != null){
			for(Method method : methods){
				OutputUtil.newLine(content);
				content.append(method.getFormattedContent(1, isInterface));
				OutputUtil.newLine(content);
			}
		}
		content.append("}");
		return content.toString();
	}

	public void calculateImports(){
		StringBuilder importBuilder = new StringBuilder(this.imports);
		if(javaAttributes.size() > 0){
			for(JavaAttribute attr:javaAttributes){
				FullyQualifiedJavaType javaType = attr.getType();
				String typePackage = javaType.getPackageName();
				if(javaType != null && !javaType.isPrimitive() && StringUtils.isNoneBlank(typePackage) && !typePackage.startsWith("java.lang")){
					importBuilder.append("import ");
					importBuilder.append(typePackage);
					importBuilder.append(";");
					OutputUtil.newLine(importBuilder);
				}
			}
		}
		if(classAnnotions.size() > 0){
			Collection<FullyQualifiedJavaType> types = classAnnotions.values();
			for(FullyQualifiedJavaType type:types){
				String typePackage = type.getPackageName();
				if(type != null && !type.isPrimitive() && StringUtils.isNoneBlank(typePackage) && !typePackage.startsWith("java.lang")){
					importBuilder.append("import ");
					importBuilder.append(typePackage);
					importBuilder.append(";");
					OutputUtil.newLine(importBuilder);
				}
			}
		}
		this.imports = importBuilder.toString();
	}
	
	public void appendImports(String imports){
		StringBuilder importBuilder = new StringBuilder(this.imports);
		importBuilder.append(imports);
		OutputUtil.newLine(importBuilder);
		this.imports = importBuilder.toString();
	}
	
	@Override
	public String getFileName() {
		return this.fileName;
	}

	@Override
	public String getTargetPackage() {
		return this.targetPackage;
	}
}
