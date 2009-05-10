package com.itsalleasy.walker;

public interface TrackingPolicy {
	boolean isTracked(Object obj);
	String getTrackedPath(Object obj);
	void track(Object obj);
	Object pushPath(Object pathNode, Object item);
	void popPath(Object pathNode, Object item, Object pushContext);
}
