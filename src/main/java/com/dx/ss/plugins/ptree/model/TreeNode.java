package com.dx.ss.plugins.ptree.model;

import java.io.Serializable;

public class TreeNode implements Serializable{

	private static final long serialVersionUID = -6907768597537702786L;
	
	private Integer id;

	private String name;
	
	private String flag;

    private Integer nlevel;

    private Integer leftId;

    private Integer rightId;
    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Integer getNlevel() {
		return nlevel;
	}

	public void setNlevel(Integer nlevel) {
		this.nlevel = nlevel;
	}

	public Integer getLeftId() {
		return leftId;
	}

	public void setLeftId(Integer leftId) {
		this.leftId = leftId;
	}

	public Integer getRightId() {
		return rightId;
	}

	public void setRightId(Integer rightId) {
		this.rightId = rightId;
	}

}
