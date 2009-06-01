package com.itsalleasy.walker;

public class IterableNodeWalker implements NodeWalker{
	private Iterable iterable;
	private NodeWalker itemWalker;
	public IterableNodeWalker(Iterable iterable, NodeWalker itemWalker){
		this.iterable = iterable;
		this.itemWalker = itemWalker;
	}
	public void walk(Object obj, WalkerVisitor visitor, Tracker tracker){
        visitor.arrayStart(obj);
        int i = 0;
        for(Object item : iterable){
        	Object pushContext = tracker.pushPath(i);
        	visitor.arrayItem(i++, item);
        	itemWalker.walk(item, visitor, tracker);
        	tracker.popPath(i, pushContext);
        }
        visitor.arrayEnd(obj);
	}
}
