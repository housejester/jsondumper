package com.itsalleasy.walker;

public class RootNodeWalker implements NodeWalker{
	private PropertyFilter filter;
	private NodeWalkerFactory walkerFactory;
	
	public RootNodeWalker(PropertyFilter filter, NodeWalkerFactory walkerFactory){
		this.filter = filter;
		this.walkerFactory = walkerFactory;
	}

	public void walk(Object obj, WalkerVisitor visitor, Tracker tracker) {
		NodeWalker walker = walkerFactory.create(obj, this, filter);
		walker.walk(obj, visitor, tracker);
	}
}
