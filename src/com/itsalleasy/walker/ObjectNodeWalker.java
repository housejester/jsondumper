package com.itsalleasy.walker;

public class ObjectNodeWalker implements NodeWalker{

	private Iterable<NameValuePair> propertyPairs;
	private NodeWalker valueWalker;
	private PropertyFilter filter;
	
	public ObjectNodeWalker(Iterable<NameValuePair> propertyPairs, NodeWalker valueWalker, PropertyFilter filter){
		this.propertyPairs = propertyPairs;
		this.valueWalker = valueWalker;
		this.filter = filter;
	}

	public void walk(Object obj, WalkerVisitor visitor, Tracker tracker) {
        visitor.beanStart(obj);
        boolean isFirst = true;
        for(NameValuePair item : propertyPairs){
        	Object value = item.getValue();
        	String name = item.getName();
        	Object pushContext = tracker.pushPath(name);
        	if(!filter.filter(value, name)){
	        	visitor.beanProperty(name, value, isFirst);
	        	isFirst = false;
	        	valueWalker.walk(value, visitor, tracker);
        	}
        	tracker.popPath(name, pushContext);
        }
        visitor.beanEnd(obj);
	}

}
