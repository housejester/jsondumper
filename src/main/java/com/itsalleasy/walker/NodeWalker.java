package com.itsalleasy.walker;

public interface NodeWalker {
	public void walk(Object obj, WalkerVisitor visitor, Tracker tracker);
}
