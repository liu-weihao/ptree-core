package com.dx.ss.plugins.ptree.generate.java;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.dx.ss.plugins.ptree.generate.GeneratedFile;
import com.dx.ss.plugins.ptree.utils.OutputUtil;

public class GeneratedJavaFile extends GeneratedFile {

	/** The file name. */
    private String fileName;
    
    /** The target package. */
    private String targetPackage;
    
	private String packageName;
	
	private String imports;
	
	private JavaVisibility visibility = JavaVisibility.PUBLIC;
	
	private boolean isInterface = false;
	
	private String className;
	
	private List<JavaAttribute> javaAttributes = null;
	
	private List<Method> methods = null;

	public GeneratedJavaFile(String targetProject, String targetPackage, String fileName) {
		super(targetProject);
		this.targetPackage = targetPackage;
		this.fileName = fileName;
		methods = new ArrayList<>();
		javaAttributes = new ArrayList<>();
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

	public void setImports(String imports) {
		this.imports = imports;
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

	public List<JavaAttribute> getJavaAttributes() {
		return javaAttributes;
	}

	public void setJavaAttributes(List<JavaAttribute> javaAttributes) {
		this.javaAttributes = javaAttributes;
	}

	public List<Method> getMethods() {
		return methods;
	}

	public void addMethod(Method method) {
		if(method != null)		this.methods.add(method);
	}
	
	public String getFormattedContent(){
		StringBuilder content = new StringBuilder();
		content.append("package ");
		content.append(packageName);
		content.append(";");
		OutputUtil.newLine(content);
		content.append(StringUtils.isBlank(imports)?StringUtils.EMPTY:imports);
		OutputUtil.newLine(content);
		content.append(visibility.getValue());
		content.append(" ");
		content.append(isInterface ? "interface" : "class");
		content.append(" ");
		content.append(className);
		content.append(" {");
		OutputUtil.newLine(content);
		
		if(javaAttributes != null){
			for(JavaAttribute attr : javaAttributes){
				content.append(attr.getVisibility().getValue());
				content.append(" ");
				content.append(attr.getType().getSimpleName());
				content.append(" ");
				content.append(attr.getAttributeName());
				content.append(";");
				OutputUtil.newLine(content);
			}
		}
		
		if(methods != null){
			for(Method method : methods){
				content.append(method.getFormattedContent(0, isInterface));
				OutputUtil.newLine(content);
			}
		}
		content.append("} ");
		return content.toString();
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
