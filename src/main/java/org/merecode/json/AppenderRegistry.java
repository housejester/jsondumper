package org.merecode.json;

public interface AppenderRegistry {
	public Appender lookupAppenderFor(Object obj);
}
