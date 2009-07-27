/**
 * 
 */
package org.merecode.json;

public class TestBooleanBean{
	private String name;
	private boolean value;
	public TestBooleanBean(String name, boolean value){
		this.name = name;
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public boolean isValue() {
		return value;
	}
}