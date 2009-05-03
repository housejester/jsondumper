package com.itsalleasy.json.serializers;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class BeanNameValuePairs implements Iterable<NameValuePair>{
	private Collection<SimpleBeanPropertyAccessor> accessors;
	private Object bean;
	private static Map<Class<?>, List<SimpleBeanPropertyAccessor>> accessorsByClass = new HashMap<Class<?>, List<SimpleBeanPropertyAccessor>>();

	public BeanNameValuePairs(Object bean) {
		this.bean = bean;
		this.accessors = BeanNameValuePairs.getAccessors(bean.getClass());
	}
	public Iterator<NameValuePair> iterator() {
		return new PropertyIterator(bean, accessors);
	}
	private static Collection<SimpleBeanPropertyAccessor> getAccessors(Class<?> clazz){
		List<SimpleBeanPropertyAccessor> accessorList = accessorsByClass.get(clazz);
		if( accessorList == null){
			accessorList = new ArrayList<SimpleBeanPropertyAccessor>();
			Method[] allMethods = clazz.getMethods();
			for(Method method: allMethods){
				String name = method.getName();
				if((name.startsWith("get")||name.startsWith("is")) && !name.equals("getClass")){
					accessorList.add(new SimpleBeanPropertyAccessor(method));
				}
			}
			Map<Class<?>, List<SimpleBeanPropertyAccessor>> newMap = new HashMap<Class<?>, List<SimpleBeanPropertyAccessor>>();
			newMap.putAll(accessorsByClass);
			newMap.put(clazz, accessorList);
			accessorsByClass = newMap;
		}
		return accessorList;
	}
}
class PropertyIterator implements Iterator<NameValuePair>{
	private Iterator<SimpleBeanPropertyAccessor> accessorIterator;
	private Object bean;
	public PropertyIterator(Object bean, Collection<SimpleBeanPropertyAccessor> accessors){
		accessorIterator = accessors.iterator();
		this.bean = bean;
	}
	public boolean hasNext() {
		return accessorIterator.hasNext();
	}
	public NameValuePair next() {
		SimpleBeanPropertyAccessor accessor = accessorIterator.next();
		return new NameValuePair(accessor.getName(), accessor.getValue(bean));
	}
	public void remove() {
	}
}
