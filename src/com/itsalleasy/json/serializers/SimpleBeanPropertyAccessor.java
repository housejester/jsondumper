package com.itsalleasy.json.serializers;

import java.lang.reflect.Method;


public class SimpleBeanPropertyAccessor implements PropertyAccessor{
	private String name;
	private Method accessor;
	public SimpleBeanPropertyAccessor(Method accessor){
		this.accessor = accessor;
		accessor.setAccessible(true);
		String accessorName = accessor.getName();
		StringBuilder propertyName = new StringBuilder();
		int propertyNameIndex = accessorName.startsWith("get")?3:2;
		propertyName.append(Character.toLowerCase(accessorName.charAt(propertyNameIndex)));
		propertyName.append(accessorName.substring(propertyNameIndex+1));
		this.name = propertyName.toString();
	}
	/* (non-Javadoc)
	 * @see com.itsalleasy.json.PropertyAccessor#getName()
	 */
	public String getName(){
		return name;
	}
	/* (non-Javadoc)
	 * @see com.itsalleasy.json.PropertyAccessor#getValue(java.lang.Object)
	 */
	public Object getValue(Object obj){
		try {
			return accessor.invoke(obj, new Object[]{});
		} catch (Exception e) {
		}
		return null;
	}
}
