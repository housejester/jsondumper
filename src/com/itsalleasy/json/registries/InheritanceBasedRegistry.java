package com.itsalleasy.json.registries;

import java.util.HashMap;
import java.util.Map;

import com.itsalleasy.json.JsonSerializer;
import com.itsalleasy.json.SerializerRegistry;
import com.itsalleasy.json.serializers.PrimitiveArraySerializer;

public class InheritanceBasedRegistry implements SerializerRegistry{

	private Map<Class<?>, JsonSerializer> serializers;
	private JsonSerializer primitiveArraySerializer;

	public InheritanceBasedRegistry() {
		serializers = new HashMap<Class<?>, JsonSerializer>();
		primitiveArraySerializer = new PrimitiveArraySerializer();
	}

	public void register(Class<?> clazz, JsonSerializer serializer) {
		serializers.put(clazz, serializer);
	}

	public JsonSerializer lookupSerializerFor(Class<?> objClass) {
		for(Map.Entry<Class<?>,JsonSerializer> entry:serializers.entrySet()){
			if(entry.getKey().isAssignableFrom(objClass)){
				return entry.getValue();
			}
		}
		if(objClass.isArray() && objClass.getComponentType().isPrimitive()){
			return primitiveArraySerializer;
		}
		return null;
	}

}