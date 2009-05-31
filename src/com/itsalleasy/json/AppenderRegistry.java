package com.itsalleasy.json;

public interface AppenderRegistry {
	public Appender lookupSerializerFor(Object obj);
}
