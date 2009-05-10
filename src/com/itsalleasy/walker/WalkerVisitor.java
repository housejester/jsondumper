package com.itsalleasy.walker;

public interface WalkerVisitor {
	void visit(Object object);
	void arrayStart(Object obj);
	void arrayEnd(Object obj);
	public void revisit(Object obj, String path);
	void arrayItem(Object item, int i);
	void beanStart(Object obj);
	void beanProperty(Object value, String name, boolean isFirst);
	void beanEnd(Object obj);
}
