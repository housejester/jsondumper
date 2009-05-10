package com.itsalleasy.walker;

public interface TrackingPolicy {
	void track(Object obj);
	boolean isTracked(Object obj);
	String getTrackedPath(Object obj);
	Object pushPath(Object pathNode);
	void popPath(Object pathNode, Object pushContext);
}
