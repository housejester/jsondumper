package com.itsalleasy.walker;

public class TrackingPolicyThatDoesNotTrackAnything implements TrackingPolicy{
	public void track(Object obj) {
	}
	public boolean isTracked(Object obj) {
		return false;
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
