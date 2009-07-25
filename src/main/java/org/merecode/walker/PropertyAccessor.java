package org.merecode.walker;

public interface PropertyAccessor {

	public abstract String getName();

	public abstract Object getValue(Object obj);

}