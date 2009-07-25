package org.merecode.walker;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


public class BeanNameValuePairs implements NameValuePairs{
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
				if(isAccessorMethod(method)){
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

	private static final Pattern IS_ACCESSOR = Pattern.compile("^(get|is)[A-Z].*");
	private static boolean isAccessorMethod(Method method) {
		String name = method.getName();
		return 
			!(Void.TYPE.equals(method.getReturnType())) &&
			!(Class.class.equals(method.getReturnType()))&&
			method.getParameterTypes().length == 0 && 
			IS_ACCESSOR.matcher(name).matches();	
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
