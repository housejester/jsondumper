package com.itsalleasy.json.registries;

import java.util.HashMap;
import java.util.Map;

import com.itsalleasy.json.JsonSerializer;
import com.itsalleasy.json.SerializerRegistry;

public class CachingSerializerRegistry implements SerializerRegistry{
	private Map<Class<?>, JsonSerializer> cache;
	private SerializerRegistry target;

	public CachingSerializerRegistry(SerializerRegistry target){
		cache = new HashMap<Class<?>, JsonSerializer>();
		this.target = target;
	}
	public JsonSerializer lookupSerializerFor(Class<?> objClass) {
		if(!cache.containsKey(objClass)){
			register(objClass, target.lookupSerializerFor(objClass));
		}
		return cache.get(objClass);
	}

	public void register(Class<?> clazz, JsonSerializer serializer) {
		cache.put(clazz, serializer);
	}
	protected SerializerRegistry getTargetRegistry(){
		return target;
	}

}
