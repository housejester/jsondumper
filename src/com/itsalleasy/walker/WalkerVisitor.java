package com.itsalleasy.walker;

public interface WalkerVisitor {
	void startWalk(Object object);
	void endWalk(Object object);
	void visit(Object object);
	void arrayStart(Object obj);
	void arrayEnd(Object obj);
	public void revisit(Object obj, String path);
	void arrayItem(int index, Object item);
	void beanStart(Object obj);
	void beanProperty(String name, Object value, boolean isFirst);
	void beanEnd(Object obj);
}
