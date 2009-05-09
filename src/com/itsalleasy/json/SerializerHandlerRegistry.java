package com.itsalleasy.json;

public interface SerializerHandlerRegistry {
	public JsonSerializeHandler lookupSerializerFor(Object obj);
}
