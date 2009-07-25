package org.merecode.walker;

public interface WalkerVisitor {
	void startWalk(Object object);
	void endWalk(Object object);
	
	void visit(Object object);
	void revisit(Object obj, String path);
	
	void beforeWalkArray(Object obj);
	void beforeVisitArrayItem(Object item, int index);
	void afterWalkArray(Object obj);

	void beforeWalkBean(Object obj);
	void beforeVisitBeanProperty(Object value, String name, boolean isFirst);
	void afterWalkBean(Object obj);
}
