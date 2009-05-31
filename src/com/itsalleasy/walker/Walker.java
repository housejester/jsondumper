package com.itsalleasy.walker;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;


public class Walker {
	private WalkerVisitor visitor;
	private PropertyFilter filter = PropertyFilters.IS_DEFAULT_OR_EMPTY;
	private Tracker trackingPolicy;
	public Walker(WalkerVisitor walkerVisitor) {
		this(walkerVisitor, null, null);
	}

	public Walker(WalkerVisitor walkerVisitor, PropertyFilter filter, Tracker trackingPolicy){
		this.visitor = walkerVisitor;
		this.filter = filter == null ? PropertyFilters.IS_DEFAULT_OR_EMPTY : filter;
		this.trackingPolicy = trackingPolicy == null ? new TrackerThatRemembersObjectsAndTheirPaths() : trackingPolicy;
	}

	public void walk(Object obj) {
		visitor.startWalk(obj);
		walkInternal(obj);
		visitor.endWalk(obj);
		visitor = null;
		trackingPolicy = null;
		
	}
	private void walkInternal(Object obj) {
		if(obj == null || !isBeanLike(obj)){
			visitor.visit(obj);
			return;
		}
		if(trackingPolicy.isTracked(obj)){
			visitor.revisit(obj, trackingPolicy.getTrackedPath(obj));
			return;
		}
		trackingPolicy.track(obj);
		visitor.visit(obj);

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
	        	Object pushContext = trackingPolicy.pushPath(i);
	        	visitor.arrayItem(i++, item);
	        	walkInternal(item);
	        	trackingPolicy.popPath(i, pushContext);
	        }
	        visitor.arrayEnd(obj);
	        return;
		}
		
		Iterable<NameValuePair> propertyPairs;
		if(obj instanceof Map){
			propertyPairs = new MapNameValuePairs((Map)obj);
		}else{
			propertyPairs = new BeanNameValuePairs(obj);
		}
        visitor.beanStart(obj);
        boolean isFirst = true;
        for(NameValuePair item : propertyPairs){
        	Object value = item.getValue();
        	String name = item.getName();
        	Object pushContext = trackingPolicy.pushPath(name);
        	if(!filter.filter(value, name)){
	        	visitor.beanProperty(name, value, isFirst);
	        	isFirst = false;
	        	walkInternal(value);
        	}
        	trackingPolicy.popPath(name, pushContext);
        }
        visitor.beanEnd(obj);
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
