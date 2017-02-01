/**
 * 
 */
package com.dx.ss.plugins.ptree.utils;

/**
 * @author panwuhai
 *
 */
public class NodeCommon {

	/**
	 * 添加子节点层
	 * 
	 * @param fatherLevel
	 * @return
	 */
	public static int addSubLevel(Integer fatherLevel) {

		return fatherLevel.intValue() + 1;
	}

	/**
	 * 新增子节点的左节点
	 * 
	 * @param fatherLeftNode
	 *            父节点左节点
	 * @return
	 */
	public static int addSubLeftNode(Integer fatherLeftNode) {

		return fatherLeftNode.intValue() + 1;
	}

	/**
	 * 新增子节点的右节点
	 * 
	 * @param fatherLeftNode
	 *            父节点左节点
	 * @return
	 */
	public static int addSubRightNode(Integer fatherLeftNode) {

		return fatherLeftNode.intValue() + 2;
	}

	/**
	 * 添加平级节点层
	 * 
	 * @param beforeLevel
	 * @return
	 */
	public static int addLevelLevel(Integer beforeLevel) {

		return beforeLevel.intValue();
	}

	/**
	 * 新增平级节点的左节点
	 * 
	 * @param levelRightNode
	 *            前平级节点的右节点
	 * @return
	 */
	public static int addLevelLeftNode(Integer levelRightNode) {
		return levelRightNode.intValue() + 1;
	}

	/**
	 * 新增平级节点的右节点
	 * 
	 * @param levelRightNode
	 *            前平级节点的右节点
	 * @return
	 */
	public static int addLevelRightNode(Integer levelRightNode) {
		return levelRightNode.intValue() + 2;
	}
}
