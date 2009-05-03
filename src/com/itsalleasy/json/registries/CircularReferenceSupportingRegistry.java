package com.itsalleasy.json.registries;

import com.itsalleasy.json.JsonSerializer;
import com.itsalleasy.json.JsonWriter;
import com.itsalleasy.json.SerializerRegistry;
import com.itsalleasy.json.serializers.ObjectReferenceSerializer;
import com.itsalleasy.json.serializers.ReferenceableSerializer;

public class CircularReferenceSupportingRegistry implements SerializerRegistry{
	private SerializerRegistry registry;
	private JsonWriter writer;
	public CircularReferenceSupportingRegistry(JsonWriter writer, SerializerRegistry delegate){
		this.registry = delegate;
		this.writer = writer;
	}
	public JsonSerializer lookupSerializerFor(Object obj) {
		JsonSerializer serializer = registry.lookupSerializerFor(obj);

		if(!(serializer instanceof ReferenceableSerializer)){
			return serializer;
		}

		String path = writer.getPathForWritten(obj);
		if(path!=null){
			return new ObjectReferenceSerializer(path);
		}

		return serializer;
	}

}
