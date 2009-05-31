package com.itsalleasy.json.registries;

import com.itsalleasy.json.JsonSerializeHandler;
import com.itsalleasy.json.SerializerHandlerRegistry;
import com.itsalleasy.json.serializers.NullLiteralSerializer;

public class NullCheckRegistry implements SerializerHandlerRegistry {
	private SerializerHandlerRegistry registry;
	private static final JsonSerializeHandler NULL_SERIALIZER = new NullLiteralSerializer();
	public NullCheckRegistry(SerializerHandlerRegistry delegate){
		registry = delegate;
	}
	public JsonSerializeHandler lookupSerializerFor(Object obj) {
		if(obj == null){
			return NULL_SERIALIZER;
		}
		return registry.lookupSerializerFor(obj);
	}

}
