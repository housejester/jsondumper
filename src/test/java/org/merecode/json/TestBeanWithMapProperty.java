/**
 * 
 */
package org.merecode.json;

import java.util.Map;

public class TestBeanWithMapProperty{
	private String name;
	@SuppressWarnings("unchecked")
	private Map map;
	@SuppressWarnings("unchecked")
	public TestBeanWithMapProperty(String name, Map map){
		this.name = name;
		this.map = map;
	}
	public String getName() {
		return name;
	}
	@SuppressWarnings("unchecked")
	public Map getMap() {
		return map;
	}
	
}