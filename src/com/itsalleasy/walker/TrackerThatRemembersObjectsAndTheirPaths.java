package com.itsalleasy.walker;

import java.util.IdentityHashMap;

public class TrackerThatRemembersObjectsAndTheirPaths implements Tracker{
	private IdentityHashMap<Object, String> seen = new IdentityHashMap<Object, String>();
	private StringBuilder pathStack = new StringBuilder();
	
	public void track(Object obj){
		seen.put(obj, pathStack.toString());
	}

	public boolean isTracked(Object obj) {
		return seen.containsKey(obj);
	}

	public String getTrackedPath(Object obj){
		return seen.get(obj);
	}
	
	public Object pushPath(Object pathNode) {
    	int stackStart = pathStack.length();
    	if(stackStart > 0){
    		pathStack.append('.');
    	}
    	pathStack.append(pathNode);
    	return stackStart;
	}

	public void popPath(Object pathNode, Object pushContext) {
		Integer stackStart = (Integer)pushContext;
    	pathStack.delete(stackStart, pathStack.length());
	}

}
