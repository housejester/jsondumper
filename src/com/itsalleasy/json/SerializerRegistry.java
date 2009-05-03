package com.itsalleasy.json;

public interface SerializerRegistry {
	public JsonSerializer lookupSerializerFor(Object obj);
}
