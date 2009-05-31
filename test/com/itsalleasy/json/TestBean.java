package com.itsalleasy.json;

public class TestBean {
	private String name;
	private Integer age;
	public TestBean(){
		
	}
	public TestBean(String name){
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public boolean equals(Object obj){
		if(obj == null || !(obj instanceof TestBean)){
			return false;
		}
		if(name == null){
			return ((TestBean)obj).name == null;
		}
		return name.equals(((TestBean)obj).name);
	}
	public int hashCode(){
		return name == null ? 0 : name.hashCode();
	}

}
