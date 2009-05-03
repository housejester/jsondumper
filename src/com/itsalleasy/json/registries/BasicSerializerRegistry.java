package com.itsalleasy.json.registries;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.itsalleasy.json.JsonSerializer;
import com.itsalleasy.json.serializers.ArraySerializer;
import com.itsalleasy.json.serializers.BeanSerializer;
import com.itsalleasy.json.serializers.BooleanSerializer;
import com.itsalleasy.json.serializers.CalendarSerializer;
import com.itsalleasy.json.serializers.CollectionSerializer;
import com.itsalleasy.json.serializers.DateSerializer;
import com.itsalleasy.json.serializers.MapSerializer;
import com.itsalleasy.json.serializers.NumberSerializer;
import com.itsalleasy.json.serializers.StringSerializer;

public class BasicSerializerRegistry extends InheritanceBasedRegistry{
	private JsonSerializer defaultSerializer;
	public BasicSerializerRegistry(){
		this(new BeanSerializer());
	}
	public BasicSerializerRegistry(JsonSerializer defaultSerializer){
		this.defaultSerializer = defaultSerializer;
		register(String.class, new StringSerializer());
		register(Number.class, new NumberSerializer());
		register(Boolean.class, new BooleanSerializer());
		register(Map.class, new MapSerializer());
		register(Collection.class, new CollectionSerializer());
		register(Calendar.class, new CalendarSerializer());
		register(Date.class, new DateSerializer());
		register(Character.class, new StringSerializer());
		register(Enum.class, new StringSerializer());
		register((new Object[]{}).getClass(), new ArraySerializer());
	}
	@Override
	public JsonSerializer lookupSerializerFor(Class<?> objClass) {
		JsonSerializer serializer = super.lookupSerializerFor(objClass);
		return serializer != null ? serializer : defaultSerializer;
	}
	
}
