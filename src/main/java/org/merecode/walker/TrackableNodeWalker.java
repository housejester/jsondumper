package org.merecode.walker;

public abstract class TrackableNodeWalker implements NodeWalker{
	public void walk(Object obj, WalkerVisitor visitor, Tracker tracker) {
		if(tracker.isTracked(obj)){
			visitor.revisit(obj, tracker.getTrackedPath(obj));
			return;
		}
		tracker.track(obj);
		visitor.visit(obj);
		doWalk(obj, visitor, tracker);
	}
	protected abstract void doWalk(Object obj, WalkerVisitor visitor, Tracker tracker);
}
