package com.dx.ss.plugins.ptree.generate.java;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hamcrest.core.IsInstanceOf;

import com.dx.ss.plugins.ptree.utils.ColumnPropertyUtil;
import com.dx.ss.plugins.ptree.utils.OutputUtil;

public class CompilationUnit {

	private String packageName;
	
	private String imports;
	
	private JavaVisibility visibility = JavaVisibility.PUBLIC;
	
	private boolean isInterface = false;
	
	private String className;
	
	private List<JavaAttribute> javaAttributes;
	
	private List<Method> methods;

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

	public void setMethods(List<Method> methods) {
		this.methods = methods;
	}
	
	public String getFormattedContent(){
		StringBuilder content = new StringBuilder();
		content.append(packageName);
		OutputUtil.newLine(content);
		content.append(imports);
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
				content.append(attr.getType().getName());
				content.append(" ");
				content.append(attr.getAttributeName());
				content.append(";");
				OutputUtil.newLine(content);
			}
		}
		
		if(methods != null){
			for(Method method : methods){
				StringBuilder sb = new StringBuilder();
	            int mod = method.getModifiers() & Modifier.methodModifiers();
	            if (mod != 0) {
	                sb.append(Modifier.toString(mod)).append(' ');
	            }
	            Class<?> returnType = method.getReturnType();
				content.append(returnType != null ? returnType.getName() : "void");
				content.append(method.getName());
	            Class<?>[] params = method.getParameterTypes(); // avoid clone
	            for (int j = 0; j < params.length; j++) {
	                String typeName = params[j].getName();
					sb.append(typeName).append(" ");
					sb.append(ColumnPropertyUtil.lowerFirstLetter(typeName)).append(" ");
	                if (j < (params.length - 1))	sb.append(',');
	                if(!isInterface){
	                	sb.append(" {");
	                }
	            }
	            sb.append(')');
			}
		}
		content.append("} ");
		return StringUtils.EMPTY;
	}
}
