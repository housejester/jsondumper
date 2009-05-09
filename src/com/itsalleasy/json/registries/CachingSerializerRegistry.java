package com.itsalleasy.json.registries;

import java.util.HashMap;
import java.util.Map;

import com.itsalleasy.json.JsonSerializeHandler;
import com.itsalleasy.json.SerializerHandlerRegistry;

public class CachingSerializerRegistry implements SerializerHandlerRegistry{
	private Map<Class<?>, JsonSerializeHandler> cache;
	private SerializerHandlerRegistry target;

	public CachingSerializerRegistry(SerializerHandlerRegistry target){
		cache = new HashMap<Class<?>, JsonSerializeHandler>();
		this.target = target;
	}
	public JsonSerializeHandler lookupSerializerFor(Object obj) {
		Class<?> objClass = obj.getClass();
		JsonSerializeHandler serializer = cache.get(objClass);
		if(serializer == null){
			serializer = target.lookupSerializerFor(obj);
			cache.put(objClass, serializer);
		}
		return serializer;
	}

	protected SerializerHandlerRegistry getTargetRegistry(){
		return target;
	}

}
