package com.itsalleasy.walker;

import java.util.IdentityHashMap;

public class TrackingPolicyThatRemembersObjects implements TrackingPolicy{
	private IdentityHashMap<Object, String> seen = new IdentityHashMap<Object,String>();

	public void track(Object obj) {
		seen.put(obj, "");
	}
	public boolean isTracked(Object obj) {
		return seen.containsKey(obj);
	}
	public String getTrackedPath(Object obj) {
		return null;
	}
	public Object pushPath(Object pathNode) {
		return null;
	}
	public void popPath(Object pathNode, Object pushContext) {
	}
}
