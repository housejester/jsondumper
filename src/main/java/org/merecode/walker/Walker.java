package org.merecode.walker;

public class Walker {
	private PropertyFilter filter;
	private TrackingPolicy trackingPolicy;
	private NodeWalkerFactory nodeWalkerFactory;

	public Walker() {
		this(null, null, null);
	}

	public Walker(PropertyFilter filter, TrackingPolicy trackingPolicy, NodeWalkerFactory walkerFactory){
		this.filter = filter == null ? PropertyFilters.FILTER_NONE : filter;
		this.trackingPolicy = trackingPolicy == null ? TrackingPolicies.TRACK_OBJECTS : trackingPolicy;
		this.nodeWalkerFactory = walkerFactory == null ? new BasicNodeWalkerFactory() : walkerFactory;
	}

	public void walk(Object obj, WalkerVisitor visitor) {
		visitor.startWalk(obj);
		(new RootNodeWalker(filter, nodeWalkerFactory)).walk(obj, visitor, trackingPolicy.createTracker());
		visitor.endWalk(obj);
	}
}
