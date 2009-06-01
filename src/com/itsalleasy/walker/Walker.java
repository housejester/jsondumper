package com.itsalleasy.walker;

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
		(new RootNodeWalker(filter)).walk(obj, visitor, trackingPolicy.createTracker());
		visitor.endWalk(obj);
	}
}
