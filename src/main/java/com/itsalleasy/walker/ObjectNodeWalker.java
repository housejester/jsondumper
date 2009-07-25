package com.itsalleasy.walker;

public class ObjectNodeWalker extends TrackableNodeWalker{

	private Iterable<NameValuePair> propertyPairs;
	private NodeWalker valueWalker;
	private PropertyFilter shouldIgnore;
	
	public ObjectNodeWalker(Iterable<NameValuePair> propertyPairs, NodeWalker valueWalker, PropertyFilter ignoreFilter){
		this.propertyPairs = propertyPairs;
		this.valueWalker = valueWalker;
		this.shouldIgnore = ignoreFilter;
	}

	@Override
	protected void doWalk(Object obj, WalkerVisitor visitor, Tracker tracker) {
        visitor.beforeWalkBean(obj);
        boolean isFirst = true;
        for(NameValuePair item : propertyPairs){
        	String name = item.getName();
        	Object value = item.getValue();
        	if(!shouldIgnore.matches(value, name)){
            	Object pushContext = tracker.pushPath(name);
	        	visitor.beforeVisitBeanProperty(value, name, isFirst);
	        	isFirst = false;
	        	valueWalker.walk(value, visitor, tracker);
	        	tracker.popPath(name, pushContext);
        	}
        }
        visitor.afterWalkBean(obj);
	}

}
