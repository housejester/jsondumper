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
		if(obj instanceof Iterable){
			return new IterableNodeWalker((Iterable)obj, parent);
		}else if(obj instanceof Object[]){
			return new IterableNodeWalker(Arrays.asList((Object[])obj), parent);
		}else if(obj != null && obj.getClass().isArray() ){
			return new IterableNodeWalker(new PrimitiveArrayIterable(obj), parent);
		}
		
		Iterable<NameValuePair> propertyPairs = null;
		if(obj instanceof Map){
			return new ObjectNodeWalker(new MapNameValuePairs((Map)obj), parent, filter);
		}
		return new ObjectNodeWalker(new BeanNameValuePairs(obj), parent, filter);
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
