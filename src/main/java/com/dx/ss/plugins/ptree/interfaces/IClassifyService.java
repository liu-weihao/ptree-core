package com.dx.ss.plugins.ptree.interfaces;

import java.util.List;

import com.dx.ss.plugins.ptree.model.TreeNode;

/**
 * <p class="detail">
 * 功能：树形结构(<a href="http://my.oschina.net/bootstrap/blog/166805">预排序遍历树算法</a>)<br/>
 * 相关操作接口定义
 * </p>
 * @ClassName: IClassifyService 
 * @version V1.0  
 * @date 2017年1月7日 
 * @author weihao.liu
 * @param <T>	具体的分类
 * Copyright 2016 tsou.com, Inc. All rights reserved
 */
public interface IClassifyService<T extends TreeNode> {

	/**
	 * <p class="detail">
	 * 功能：商品分类树形结构
	 * </p>
	 * @author weihao.liu
	 * @date 2016-7-20 
	 * @return
	 */
	public String getClassifyTree();
	
	/**
	 * <p class="detail">
	 * 功能：获取分类列表
	 * </p>
	 * @author weihao.liu
	 * @date 2016-7-20 
	 * @param parentId	如果为null，表示获取所有分类，否则获取指定分类的<strong>直接子类</strong>
	 * @param includeSelf  是否包含父类本身
	 * @return
	 */
	public List<T> classifyList(Integer parentId, boolean includeSelf);
	
	/**
	 * <p class="detail">
	 * 功能：获取某个层级的分类
	 * </p>
	 * @author weihao.liu
	 * @date 2016年12月19日 
	 * @param level	分类层级，从1开始
	 * @return
	 */
	public List<T> classifyListByLevel(Integer level);
	
	/**
	 * <p class="detail">
	 * 功能：根据子类id，获取父类以及父类的同级分类
	 * </p>
	 * @author weihao.liu
	 * @date 2017年1月14日 
	 * @param children
	 * @return
	 */
	public List<T> getParentSiblings(Integer children);
	
	/**
	 * <p class="detail">
	 * 功能：根据子类id，获取父类信息
	 * </p>
	 * @author weihao.liu
	 * @date 2017年1月14日 
	 * @param children	子类id
	 * @return
	 */
	public T getParentClassify(Integer children);
	
	/**
	 * <p class="detail">
	 * 功能：根据id获取分类详情
	 * </p>
	 * @author liu.weihao
	 * @date 2016-7-20 
	 * @param classifyId
	 * @return
	 */
	public T getClassifyInfoById(Integer classifyId);
	
	/**
	 * <p class="detail">
	 * 功能：生成一个新节点
	 * </p>
	 * @author liu.weihao
	 * @date 2016-7-20 
	 * @param classify	要添加的节点
	 * @param parentId  父节点id
     * @param preId 前置节点，最前端则不传入
	 * @return
	 */
	public T mallocClassify(T classify, Integer parentId, Integer preId);
	
	/**
	 * <p class="detail">
	 * 功能：添加类别
	 * </p>
	 * @author liu.weihao
	 * @date 2016-7-20 
	 * @param classify
	 * @return
	 */
	public boolean addClassify(T classify);
	
	/**
	 * <p class="detail">
	 * 功能：修改分类，不传入分类id将会修改失败
	 * </p>
	 * @author liu.weihao
	 * @date 2016年4月14日 
	 * @param classify
	 * @return
	 */
	public boolean editClassify(T classify);
	
	/**
	 * <p class="detail">
	 * 功能：删除分类信息
	 * </p>
	 * @author liu.weihao
	 * @date 2016年4月18日 
	 * @param classifyId
	 * @return
	 */
	public boolean removeClassifyById(Integer classifyId);

}
