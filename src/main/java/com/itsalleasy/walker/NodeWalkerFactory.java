package com.itsalleasy.walker;

public interface NodeWalkerFactory {
	public NodeWalker create(Object obj, NodeWalker parent, PropertyFilter filter);
}