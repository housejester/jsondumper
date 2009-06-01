package com.itsalleasy.walker;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class Walker {
	private PropertyFilter filter;
	private TrackingPolicy trackingPolicy;
	public Walker() {
		this(null, null);
	}

	public Walker(PropertyFilter filter, TrackingPolicy trackingPolicy){
		this.filter = filter == null ? PropertyFilters.FILTER_NONE : filter;
		this.trackingPolicy = trackingPolicy == null ? TrackingPolicies.TRACK_OBJECTS : trackingPolicy;
	}

	public void walk(Object obj, WalkerVisitor visitor) {
		visitor.startWalk(obj);
		walkInternal(obj, visitor, trackingPolicy.createTracker());
		visitor.endWalk(obj);
		visitor = null;
	}
	private void walkInternal(Object obj, WalkerVisitor visitor, Tracker tracker) {
		if(obj == null || !isBeanLike(obj)){
			visitor.visit(obj);
			return;
		}
		if(tracker.isTracked(obj)){
			visitor.revisit(obj, tracker.getTrackedPath(obj));
			return;
		}
		tracker.track(obj);
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
	        	Object pushContext = tracker.pushPath(i);
	        	visitor.arrayItem(i++, item);
	        	walkInternal(item, visitor, tracker);
	        	tracker.popPath(i, pushContext);
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
        	Object pushContext = tracker.pushPath(name);
        	if(!filter.filter(value, name)){
	        	visitor.beanProperty(name, value, isFirst);
	        	isFirst = false;
	        	walkInternal(value, visitor, tracker);
        	}
        	tracker.popPath(name, pushContext);
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
