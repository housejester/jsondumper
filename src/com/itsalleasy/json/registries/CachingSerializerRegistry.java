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
	public JsonSerializer lookupSerializerFor(Object obj) {
		Class<?> objClass = obj.getClass();
		JsonSerializer serializer = cache.get(objClass);
		if(serializer == null){
			serializer = target.lookupSerializerFor(obj);
			cache.put(objClass, serializer);
		}
		return serializer;
	}

	protected SerializerRegistry getTargetRegistry(){
		return target;
	}

}
