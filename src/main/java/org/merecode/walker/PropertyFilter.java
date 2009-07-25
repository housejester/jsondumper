package org.merecode.walker;

public interface PropertyFilter {
	public boolean matches(Object value, String name);
}
