package com.itsalleasy.walker;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import com.itsalleasy.json.PropertyFilter;
import com.itsalleasy.json.PropertyFilters;
import com.itsalleasy.json.serializers.BeanNameValuePairs;
import com.itsalleasy.json.serializers.MapNameValuePairs;
import com.itsalleasy.json.serializers.NameValuePair;

public class Walker {
	private WalkerVisitor visitor;
	private PropertyFilter filter = PropertyFilters.IS_DEFAULT_OR_EMPTY;
	private TrackingPolicy trackingPolicy;
	public Walker(WalkerVisitor walkerVisitor) {
		this(walkerVisitor, new TrackingPolicyThatRemembersObjectsAndTheirPaths());
	}

	public Walker(WalkerVisitor walkerVisitor, TrackingPolicy trackingPolicy){
		this.visitor = walkerVisitor;
		this.trackingPolicy = trackingPolicy;
	}

	public void walk(Object obj) {
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
	        	visitor.arrayItem(item, i++);
	        	walk(item);
	        	trackingPolicy.popPath(i, pushContext);
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
	        	Object pushContext = trackingPolicy.pushPath(name);
	        	if(!filter.filter(value, name)){
		        	visitor.beanProperty(value, name, isFirst);
		        	isFirst = false;
		        	walk(value);
	        	}
	        	trackingPolicy.popPath(name, pushContext);
	        }
	        visitor.beanEnd(obj);
	        return;
		}
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
