package com.itsalleasy.walker;

public class RootNodeWalker implements NodeWalker{
	private PropertyFilter filter;
	private NodeWalkerFactory walkerFactory;
	
	public RootNodeWalker(PropertyFilter filter){
		this.filter = filter;
		walkerFactory = new BasicNodeWalkerFactory();
	}

	public void walk(Object obj, WalkerVisitor visitor, Tracker tracker) {
		NodeWalker walker = walkerFactory.create(obj, this, filter);
		walker.walk(obj, visitor, tracker);
	}
}
