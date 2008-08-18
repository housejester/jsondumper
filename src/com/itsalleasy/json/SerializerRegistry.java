package com.itsalleasy.json;

public interface SerializerRegistry {
	public void register(Class<?> clazz, JsonSerializer serializer);
	public JsonSerializer lookupSerializerFor(Class<?> objClass);
}
