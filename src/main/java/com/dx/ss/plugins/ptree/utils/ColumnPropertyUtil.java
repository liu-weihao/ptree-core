package com.dx.ss.plugins.ptree.utils;

import org.apache.commons.lang3.StringUtils;

public class ColumnPropertyUtil {

	public static String getPropertyFromColumn(String column) {
        StringBuilder result = new StringBuilder();
        // 快速检查
        if (column == null || column.isEmpty()) {
            // 没必要转换
            return "";
        } else if (!column.contains("_")) {
            // 不含下划线，仅将首字母小写
            return column.substring(0, 1).toLowerCase() + column.substring(1);
        } else {
            // 用下划线将原始字符串分割
            String[] columns = column.split("_");
            for (String columnSplit : columns) {
                // 跳过原始字符串中开头、结尾的下换线或双重下划线
                if (columnSplit.isEmpty()) {
                    continue;
                }
                // 处理真正的驼峰片段
                if (result.length() == 0) {
                    // 第一个驼峰片段，全部字母都小写
                    result.append(columnSplit.toLowerCase());
                } else {
                    // 其他的驼峰片段，首字母大写
                    result.append(columnSplit.substring(0, 1).toUpperCase()).append(columnSplit.substring(1).toLowerCase());
                }
            }
            return result.toString();
        }
    }
	
	public static String getColumnFromProperty(String property){ 
        if (property == null || property.isEmpty()){
            return ""; 
        } 
        StringBuilder column = new StringBuilder(); 
        column.append(property.substring(0,1).toLowerCase()); 
        for (int i = 1; i < property.length(); i++) { 
            String s = property.substring(i, i + 1); 
            // 在小写字母前添加下划线 
            if(!Character.isDigit(s.charAt(0)) && s.equals(s.toUpperCase())){ 
                column.append("_"); 
            } 
            // 其他字符直接转成小写 
            column.append(s.toLowerCase()); 
        } 
        return column.toString(); 
    }
	
	public static String lowerFirstLetter(String str){
		if(StringUtils.isBlank(str))	return StringUtils.EMPTY;
		char firstLetter = str.charAt(0);
		if (firstLetter >= 'A' && firstLetter <= 'Z') {
			String temp = new String(new char[]{firstLetter});
			return str.replaceFirst(temp, temp.toLowerCase());
		}
		return str;
	}
	
	public static String upperFirstLetter(String str){
		if(StringUtils.isBlank(str))	return StringUtils.EMPTY;
		char firstLetter = str.charAt(0);
		if (firstLetter >= 'a' && firstLetter <= 'z') {
			String temp = new String(new char[]{firstLetter});
			return str.replaceFirst(temp, temp.toUpperCase());
		}
		return str;
	}
	
}
