package org.merecode.walker;

public class IterableNodeWalker extends TrackableNodeWalker{
	private Iterable iterable;
	private NodeWalker itemWalker;
	public IterableNodeWalker(Iterable iterable, NodeWalker itemWalker){
		this.iterable = iterable;
		this.itemWalker = itemWalker;
	}
	@Override
	protected void doWalk(Object obj, WalkerVisitor visitor, Tracker tracker) {
        visitor.beforeWalkArray(obj);
        int i = 0;
        for(Object item : iterable){
        	Object pushContext = tracker.pushPath(i);
        	visitor.beforeVisitArrayItem(item, i++);
        	itemWalker.walk(item, visitor, tracker);
        	tracker.popPath(i, pushContext);
        }
        visitor.afterWalkArray(obj);
	}
}
