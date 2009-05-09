package com.itsalleasy.walker;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.IdentityHashMap;
import java.util.Map;

import com.itsalleasy.json.PropertyFilter;
import com.itsalleasy.json.PropertyFilters;
import com.itsalleasy.json.serializers.BeanNameValuePairs;
import com.itsalleasy.json.serializers.MapNameValuePairs;
import com.itsalleasy.json.serializers.NameValuePair;

public class Walker {
	private WalkerVisitor visitor;
	private PropertyFilter filter = PropertyFilters.IS_DEFAULT_OR_EMPTY;
	private IdentityHashMap<Object, Boolean> seen = new IdentityHashMap<Object, Boolean>();
	public Walker(WalkerVisitor walkerVisitor) {
		this.visitor = walkerVisitor;
	}

	public void walk(Object obj) {
		if(obj == null || !isBeanLike(obj)){
			visitor.visit(obj);
			return;
		}
		if(seen.containsKey(obj)){
			visitor.revisit(obj);
			return;
		}
		visitor.visit(obj);
		seen.put(obj, true);

		Iterable iterable = null;
		if(obj instanceof Iterable){
			iterable = (Iterable)obj;
		}else if(obj instanceof Object[]){
			iterable = Arrays.asList((Object[])obj);
		}else if(obj != null && obj.getClass().isArray() ){
			iterable = new PrimitiveArrayIterable(obj);
		}
		if(iterable != null){
	        visitor.arrayStart(obj);
	        int i = 0;
	        for(Object item : iterable){
	        	visitor.arrayItem(item, i++);
	        	walk(item);
	        }
	        visitor.arrayEnd(obj);
	        return;
		}
		
		if(obj != null){
			Iterable<NameValuePair> propertyPairs;
			if(obj instanceof Map){
				propertyPairs = new MapNameValuePairs((Map)obj);
			}else{
				propertyPairs = new BeanNameValuePairs(obj);
			}
	        visitor.beanStart(obj);
	        int i = 0;
	        boolean isFirst = true;
	        for(NameValuePair item : propertyPairs){
	        	Object value = item.getValue();
	        	String name = item.getName();
	        	if(!filter.filter(value, name)){
		        	visitor.beanProperty(value, name, isFirst);
		        	isFirst = false;
		        	walk(value);
	        	}
	        }
	        visitor.beanEnd(obj);
	        return;
		}
	}

	private boolean isBeanLike(Object obj) {
		return !(
				obj.getClass().isPrimitive() || 
				obj instanceof String || 
				obj instanceof Number || 
				obj instanceof Boolean ||
				obj instanceof Date || 
				obj instanceof Calendar || 
				obj instanceof Enum 
		);
	}

}
