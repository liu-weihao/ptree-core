package com.dx.ss.plugins.ptree.dao;

import java.util.List;

import com.dx.ss.plugins.ptree.model.TreeNode;

/**
 * <p class="detail">
 * 功能：树形结构共用(<a href="http://my.oschina.net/bootstrap/blog/166805">预排序遍历树算法</a>)<br/>
 * <strong>不建议做批量删除功能</strong>
 * </p>
 * @ClassName: TreeDao 
 * @version V1.0  
 * @date 2016-7-20 
 * @author liu.weihao
 * Copyright 2016 tsou.com, Inc. All rights reserved
 */
public abstract class TreeDao<T extends TreeNode> {

	/**
	 * <p class="detail">
	 * 功能：获取分类列表
	 * </p>
	 * @author liu.weihao
	 * @date 2016-7-20 
	 * @param parentId	如果为null，表示获取所有分类，否则获取指定分类的<strong>直接子类</strong>
	 * @param includeSelf 查询子节点时，是否包含父节点本身。仅当{@code parentId!=null}时生效
	 * @return
	 */
	public abstract List<T> classifyList(Integer parentId, boolean includeSelf);
	
	/**
	 * <p class="detail">
	 * 功能：获取指定层级的分类
	 * </p>
	 * @author weihao.liu
	 * @date 2017年1月14日 
	 * @param level	分类层级，从1开始
	 * @return
	 */
	public abstract List<T> classifyListByLevel(Integer level);
	
	/**
	 * <p class="detail">
	 * 功能：根据子类id，获取父类以及父类的同级分类
	 * </p>
	 * @author weihao.liu
	 * @date 2017年1月14日 
	 * @param children
	 * @return
	 */
	public abstract List<T> getParentSiblings(Integer children);
	
	/**
	 * <p class="detail">
	 * 功能：获取分类树形结构数据
	 * </p>
	 * @author liu.weihao
	 * @date 2016-7-20 
	 * @return
	 */
	public abstract String classifyTree();
	
	/**
	 * <p class="detail">
	 * 功能：根据id获取分类详情
	 * </p>
	 * @author liu.weihao
	 * @date 2016-7-20 
	 * @param classifyId
	 * @return
	 */
	public abstract T getClassifyInfoById(Integer classifyId);
	
	/**
	 * <p class="detail">
	 * 功能：根据子类id，获取父类信息
	 * </p>
	 * @author weihao.liu
	 * @date 2017年1月14日 
	 * @param children	子类id
	 * @return
	 */
	public abstract T getParentClassify(Integer children);
	
	/**
	 * <p class="detail">
	 * 功能：新增分类
	 * </p>
	 * @author liu.weihao
	 * @date 2016年4月14日 
	 * @param classify
	 * @return
	 */
	public abstract Integer addClassify(T classify);

	/**
	 * <p class="detail">
	 * 功能：修改分类，不传入分类id将会修改失败
	 * </p>
	 * @author liu.weihao
	 * @date 2016年4月14日 
	 * @param classify
	 * @return
	 */
	public abstract boolean editClassify(T classify);
	
	/**
	 * <p class="detail">
	 * 功能：删除分类信息
	 * </p>
	 * @author liu.weihao
	 * @date 2016年4月18日 
	 * @param id
	 * @return
	 */
	public abstract boolean removeClassifyById(Integer classifyId);
	
	/**
	 * <p class="detail">
	 * 功能：插入节点，分配左节点，+2
	 * </p>
	 * @author liu.weihao
	 * @date 2016-7-21 
	 * @param classify
	 * @return
	 */
	public abstract int allocateLeftId(T classify);

	/**
	 * <p class="detail">
	 * 功能：插入节点，分配右节点，+2
	 * </p>
	 * @author liu.weihao
	 * @date 2016-7-21 
	 * @param classify
	 * @return
	 */
	public abstract int allocateRightId(T classify);

	/**
	 * <p class="detail">
	 * 功能：删除节点，回收左节点，都减去2
	 * </p>
	 * @author liu.weihao
	 * @date 2016年4月18日 
	 * @param leftId
	 * @return
	 */
	public abstract int recycleLeftId(Integer leftId);
	
	/**
	 * <p class="detail">
	 * 功能：删除节点，回收右节点，都减去2
	 * </p>
	 * @author liu.weihao
	 * @date 2016年4月18日 
	 * @param rightId
	 * @return
	 */
	public abstract int recycleRightId(Integer rightId);
}
