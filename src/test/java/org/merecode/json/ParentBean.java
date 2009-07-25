package org.merecode.json;

import java.util.List;

public class ParentBean extends TestBean{
	private String name;
	private List<? extends TestBean> children;
	public ParentBean(){
	
	}
	public ParentBean(String name){
		setName(name);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<? extends TestBean> getChildren() {
		return children;
	}
	public void setChildren(List<? extends TestBean> children) {
		this.children = children;
	}

}
