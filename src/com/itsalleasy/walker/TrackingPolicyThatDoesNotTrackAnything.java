package com.itsalleasy.walker;

public class TrackingPolicyThatDoesNotTrackAnything implements TrackingPolicy{
	public boolean isTracked(Object obj) {
		return false;
	}

	public Object pushPath(Object pathNode, Object item) {
		return null;
	}

	public void popPath(Object pathNode, Object item, Object pushContext) {
	}

	public String getTrackedPath(Object obj) {
		return null;
	}

	public void track(Object obj) {
	}
}
