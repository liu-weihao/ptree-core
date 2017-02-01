package com.dx.ss.plugins.ptree.impl;

import java.util.List;

import com.dx.ss.plugins.ptree.dao.TreeDao;
import com.dx.ss.plugins.ptree.interfaces.IClassifyService;
import com.dx.ss.plugins.ptree.model.TreeNode;
import com.dx.ss.plugins.ptree.utils.NodeCommon;

/** <p class="detail">
 * 功能：分类通用操作业务类
 * </p>
 * @ClassName: ClassifyService 
 * @version V1.0  
 * @date 2016-7-20 
 * @author liu.weihao
 * Copyright 2016 tsou.com, Inc. All rights reserved
 */
public abstract class ClassifyService<Node extends TreeNode, T extends TreeDao<Node>> implements IClassifyService<Node> {

	private T classifyDao;
	
	/**
	 * <p class="detail">
	 * 功能：分类树形结构
	 * </p>
	 * @author liu.weihao
	 * @date 2016-7-20 
	 * @return
	 */
	@Override
	public String getClassifyTree(){
		return	classifyDao.classifyTree();
	}
	
	/**
	 * <p class="detail">
	 * 功能：获取分类列表
	 * </p>
	 * @author liu.weihao
	 * @date 2016-7-20 
	 * @param parentId	如果为null，表示获取所有分类，否则获取指定分类的<strong>直接子类</strong>
	 * @param includeSelf  是否包含父类本身
	 * @return
	 */
	@Override
	public List<Node> classifyList(Integer parentId, boolean includeSelf){
		return classifyDao.classifyList(parentId, includeSelf);
	}
	
	/**
	 * <p class="detail">
	 * 功能：根据id获取分类详情
	 * </p>
	 * @author liu.weihao
	 * @date 2016-7-20 
	 * @param classifyId
	 * @return
	 */
	@Override
	public Node getClassifyInfoById(Integer classifyId){
		if(classifyId != null && classifyId > 0)		return (Node) classifyDao.getClassifyInfoById(classifyId);
		return null;
	}
	
	/**
	 * <p class="detail">
	 * 功能：根据子类id，获取父类信息
	 * </p>
	 * @author weihao.liu
	 * @date 2017年1月14日 
	 * @param children	子类id
	 * @return
	 */
	@Override
	public Node getParentClassify(Integer children) {
		return classifyDao.getParentClassify(children);
	}

	@Override
	public List<Node> classifyListByLevel(Integer level) {
		return classifyDao.classifyListByLevel(level);
	}

	@Override
	public List<Node> getParentSiblings(Integer children) {
		return classifyDao.getParentSiblings(children);
	}

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
	@Override
	public Node mallocClassify(Node classify, Integer parentId, Integer preId){
	  	//添加分类，计算新加入的分类的左右节点和层级
		int nlevel = 0, leftId = 0, rightId = 0;
        Node referClassify = null;
        if(parentId != null && parentId > 0 && preId == 0){  //最前端
        	referClassify = (Node) classifyDao.getClassifyInfoById(parentId);
        	nlevel = NodeCommon.addSubLevel(referClassify.getNlevel());
        	leftId = NodeCommon.addSubLeftNode(referClassify.getLeftId());
        	rightId = NodeCommon.addSubRightNode(referClassify.getLeftId());
        }else if(preId != null && preId > 0){	//已选择前置节点
        	referClassify = (Node) classifyDao.getClassifyInfoById(preId);
        	nlevel = referClassify.getNlevel();
        	leftId = NodeCommon.addSubLeftNode(referClassify.getRightId());
        	rightId = NodeCommon.addSubRightNode(referClassify.getRightId());
        }
        classify.setNlevel(nlevel);
        classify.setLeftId(leftId);
        classify.setRightId(rightId);
		return classify;
	}

	/**
	 * <p class="detail">
	 * 功能：添加类别
	 * </p>
	 * @author liu.weihao
	 * @date 2016-7-20 
	 * @param classify
	 * @return
	 */
	@Override
	public boolean addClassify(Node classify){
		classifyDao.allocateLeftId(classify);
		classifyDao.allocateRightId(classify);
		if(classifyDao.addClassify(classify) != null){
	        return true;
		}
		return false;
	}
	
	/**
	 * <p class="detail">
	 * 功能：修改分类，不传入分类id将会修改失败
	 * </p>
	 * @author liu.weihao
	 * @date 2016年4月14日 
	 * @param classify
	 * @return
	 */
	@Override
	public boolean editClassify(Node classify){
		
		return classifyDao.editClassify(classify);
	}
	
	/**
	 * <p class="detail">
	 * 功能：删除分类信息
	 * </p>
	 * @author liu.weihao
	 * @date 2016年4月18日 
	 * @param classifyId
	 * @return
	 */
	@Override
	public boolean removeClassifyById(Integer classifyId){
		Node classifyInfo = getClassifyInfoById(classifyId);
		Integer leftId = classifyInfo.getLeftId(), rightId = classifyInfo.getRightId();
		if(classifyDao.removeClassifyById(classifyId)){
			classifyDao.recycleLeftId(leftId);
			classifyDao.recycleRightId(rightId);
			return true;
		}
		return false;
	}
	
}
