package com.itsalleasy.walker;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class BasicNodeWalkerFactory implements NodeWalkerFactory{
	private static final NodeWalker VISIT_ONLY = new NodeWalker(){
		public void walk(Object obj, WalkerVisitor visitor, Tracker tracker) {
			visitor.visit(obj);
		}
	}; 

	public NodeWalker create(Object obj, NodeWalker parent, PropertyFilter filter) {
		if(obj == null || !isBeanLike(obj)){
			return VISIT_ONLY;
		}
		Iterable iterable = toIterable(obj);
		if(iterable instanceof NameValuePairs){
			return new ObjectNodeWalker((NameValuePairs)iterable, parent, filter);
		}

		return new IterableNodeWalker(iterable, parent);
	}

	private Iterable toIterable(Object obj){
		if(obj instanceof Iterable){
			return (Iterable)obj;
		}
		if(obj instanceof Object[]){
			return Arrays.asList((Object[])obj);
		}
		if(obj != null && obj.getClass().isArray() ){
			return new PrimitiveArrayIterable(obj);
		}
		
		Iterable<NameValuePair> propertyPairs = null;
		if(obj instanceof Map){
			return new MapNameValuePairs((Map)obj);
		}
		return new BeanNameValuePairs(obj);
	}
	private boolean isBeanLike(Object obj) {
		return !(
				obj instanceof String ||
				obj instanceof Number ||
				obj instanceof Boolean || 
				obj instanceof Date || 
				obj instanceof Character ||
				obj instanceof Calendar || 
				obj instanceof Enum 
		);
	}

}
