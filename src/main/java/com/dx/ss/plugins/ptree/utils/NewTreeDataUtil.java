package com.dx.ss.plugins.ptree.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.dx.ss.plugins.ptree.model.TreeNode;

/**
 * 树形结构数据相关操作方法
 * @author Frank.Liu
 */
public class NewTreeDataUtil {

    /**
     * 获取zTree树形结构数据
     * @param result	返回的结构数据
     * @param dataList	数据源，单个实体对象必须是继承自{@link TreeNode}
     * @param isExpend	 初始是否展开节点    true-展开	false-收缩
     * @param isHref 	是否点击打开链接
     * @param isDrag 	是否可以拖动
     * @return
     */
    public static String getZTreeJson(StringBuffer result, List<? extends TreeNode> dataList,
                                      boolean isExpend, boolean isHref, boolean isDrag) {
        if (result == null)
            result = new StringBuffer();
        for (int i = 0; dataList != null && i < dataList.size(); i++) {
            TreeNode node = (TreeNode) dataList.get(i);
            if (hasChildMenu(dataList, i)) {
                if (i > 0) result.append(",");
                result.append("{\"id\":");
                result.append(node.getId());

                result.append(",\"name\":\"");
                result.append(node.getName());

                result.append("\",\"icon\":\"");
                result.append("");

                result.append("\",\"drag\":"); //zTree，是否可以拖拽该节点
                result.append(isDrag);

                result.append(",\"open\":"); //是否展开树节点，兼容zTree
                result.append(isExpend);

                result.append(",\"expanded\":"); //是否展开树节点，兼容bui tree
                result.append(isExpend);

                result.append(",\"children\":[");
                List<TreeNode> children = getChildMenu(dataList, i);
                getZTreeJson(result, children, isExpend, isHref, isDrag);
                result.append("]}");
                i += children.size();
            } else {
                if (i > 0) {
                    result.append(",");
                }
                result.append("{\"id\":");
                result.append(node.getId());

                result.append(",\"name\":\"");
                result.append(node.getName());

                result.append("\",\"icon\":\"");
                result.append("");

                result.append("\",\"drag\":"); //zTree，是否可以拖拽该节点
                result.append(isDrag);

                result.append(",\"open\":"); //是否展开树节点，兼容zTree
                result.append(isExpend);

                result.append(",\"expanded\":"); //是否展开树节点，兼容bui tree
                result.append(isExpend);
                result.append("}");
            }
        }
        return result.toString();
    }

    /**
     * <p class="detail">
     * 功能：App树形结构组装
     * </p>
     * @author liu.weihao
     * @date 2016年4月18日 
     * @param result
     * @param dataList
     * @return
     */
    public static String getTreeJson(StringBuilder result, List<? extends TreeNode> dataList) {
        if (result == null)
            result = new StringBuilder();
        for (int i = 0; dataList != null && i < dataList.size(); i++) {
            TreeNode node = (TreeNode) dataList.get(i);
            if (hasChildMenu(dataList, i)) {
                if (i > 0)
                    result.append(",");
                result.append("{\"id\":");
                result.append(node.getId());

                result.append(",\"name\":\"");
                result.append(node.getName());
                
                result.append("\",\"level\":\"");
                result.append(node.getNlevel());

                result.append("\",\"children\":[");
                List<TreeNode> children = getChildMenu(dataList, i);
                getTreeJson(result, children);
                result.append("]}");
                i += children.size();
            } else {
                if (i > 0) {
                    result.append(",");
                }
                result.append("{\"id\":");
                result.append(node.getId());

                result.append("\",\"level\":\"");
                result.append(node.getNlevel());
                
                result.append("\",\"name\":\"");
                result.append(node.getName());

                result.append("\"}");
            }
        }
        return result.toString();
    }
    
    
    
    /**
     * 判断某个节点是否有子节点
     * @param dataList	数据源，单个实体对象必须是继承自{@link TreeNode}
     * @param index	当前节点的索引值
     * @return
     */
    private static boolean hasChildMenu(List<?> dataList, int index) {
        boolean flag = false;
        if (dataList != null && index < dataList.size() - 1) {
            TreeNode curMenu = (TreeNode) dataList.get(index); //当前菜单项
            TreeNode nextMenu = (TreeNode) dataList.get(index + 1); //下一个菜单项
            int curLevel = curMenu.getNlevel(); //当前菜单层级
            int nextLevel = nextMenu.getNlevel(); //下个菜单层级
            if (nextLevel > curLevel) {
                flag = true;
            } 
        }
        return flag;
    }

    /**
     * 获取所有的子节点
     * @param dataList	数据源，单个实体对象必须是继承自{@link TreeNode}
     * @param index	当前节点的索引值
     * @return
     */
    private static List<TreeNode> getChildMenu(List<? extends TreeNode> dataList, int index) {
        List<TreeNode> children = new ArrayList<TreeNode>();
        TreeNode menu = (TreeNode) dataList.get(index);
        int curLevel = menu.getNlevel();
        for (int i = index + 1; i < dataList.size(); i++) {
            TreeNode nextMenu = (TreeNode) dataList.get(i);
            int nextLevel = nextMenu.getNlevel();
            if (nextLevel > curLevel) {
                children.add(nextMenu);
            } else {
                break;
            }
        }
        return children;
    }

    /**
     * 将数据源转换成{@link TreeNode}
     * @param dataList	数据源，单个实体对象必须是继承自{@link TreeNode}，TreeNode内的属性皆为树形结构数据共有的，name属性名可以在子类中定义
     * @param methodName	通过反射调用该方法，设置name的值
     * @return
     */
    public static List<TreeNode> convertDataList(List<? extends TreeNode> dataList,
                                                 String methodName) {
        List<TreeNode> nodeList = new ArrayList<TreeNode>();
        try {
            TreeNode node = null;
            String name = "";
            for (Object obj : dataList) {
                Class<?> cls = obj.getClass();
                Method method = cls.getMethod(methodName);
                if (method != null) {
                    name = (String) method.invoke(obj);
                }
                node = (TreeNode) obj;
                node.setName(name);
                nodeList.add(node);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nodeList;
    }
}
