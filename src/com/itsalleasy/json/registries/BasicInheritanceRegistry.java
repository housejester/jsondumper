package com.itsalleasy.json.registries;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.itsalleasy.json.JsonSerializeHandler;
import com.itsalleasy.json.SerializerHandlerRegistry;
import com.itsalleasy.json.serializers.ArraySerializer;
import com.itsalleasy.json.serializers.BeanSerializer;
import com.itsalleasy.json.serializers.BooleanSerializer;
import com.itsalleasy.json.serializers.CalendarSerializer;
import com.itsalleasy.json.serializers.CollectionSerializer;
import com.itsalleasy.json.serializers.DateSerializer;
import com.itsalleasy.json.serializers.MapSerializer;
import com.itsalleasy.json.serializers.NumberSerializer;
import com.itsalleasy.json.serializers.PrimitiveArraySerializer;
import com.itsalleasy.json.serializers.StringSerializer;

public class BasicInheritanceRegistry implements SerializerHandlerRegistry{

	private Map<Class<?>, JsonSerializeHandler> serializers = new LinkedHashMap<Class<?>, JsonSerializeHandler>();
	private JsonSerializeHandler primitiveArraySerializer = new PrimitiveArraySerializer();
	private JsonSerializeHandler arraySerializer = new ArraySerializer();
	private JsonSerializeHandler defaultSerializer;

	public BasicInheritanceRegistry() {
		this(new BeanSerializer());
	}

	public BasicInheritanceRegistry(JsonSerializeHandler defaultSerializer){
		this.defaultSerializer = defaultSerializer;
		registerBasicSerializers();
	}

	private void registerBasicSerializers() {
		register(String.class, new StringSerializer());
		register(Number.class, new NumberSerializer());
		register(Boolean.class, new BooleanSerializer());
		register(Map.class, new MapSerializer());
		register(Collection.class, new CollectionSerializer());
		register(Calendar.class, new CalendarSerializer());
		register(Date.class, new DateSerializer());
		register(Character.class, new StringSerializer());
		register(Enum.class, new StringSerializer());
	}


	public void register(Class<?> clazz, JsonSerializeHandler serializer) {
		serializers.put(clazz, serializer);
	}

	public JsonSerializeHandler lookupSerializerFor(Object obj) {
		Class<?> objClass = obj.getClass();

		JsonSerializeHandler serializer = serializers.get(objClass);
		if(serializer != null){
			return serializer;
		}

		if(objClass.isArray()){
			if(objClass.getComponentType().isPrimitive()){
				return primitiveArraySerializer;
			}
			return arraySerializer;
		}

		for(Map.Entry<Class<?>,JsonSerializeHandler> entry:serializers.entrySet()){
			if(entry.getKey().isAssignableFrom(objClass)){
				return entry.getValue();
			}
		}
		return defaultSerializer;
	}

}