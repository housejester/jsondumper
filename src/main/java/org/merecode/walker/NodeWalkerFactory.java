package org.merecode.walker;

public interface NodeWalkerFactory {
	public NodeWalker create(Object obj, NodeWalker parent, PropertyFilter filter);
}
