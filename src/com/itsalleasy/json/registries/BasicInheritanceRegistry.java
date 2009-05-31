package com.itsalleasy.json.registries;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.itsalleasy.json.Appender;
import com.itsalleasy.json.AppenderRegistry;
import com.itsalleasy.json.appenders.BooleanAppender;
import com.itsalleasy.json.appenders.CalendarAppender;
import com.itsalleasy.json.appenders.DateAppender;
import com.itsalleasy.json.appenders.IgnoringAppender;
import com.itsalleasy.json.appenders.NumberAppender;
import com.itsalleasy.json.appenders.StringAppender;

public class BasicInheritanceRegistry implements AppenderRegistry{

	private Map<Class<?>, Appender> serializers = new LinkedHashMap<Class<?>, Appender>();
	private Appender defaultAppender;

	public BasicInheritanceRegistry() {
		this(new IgnoringAppender());
	}

	public BasicInheritanceRegistry(Appender defaultSerializer){
		this.defaultAppender = defaultSerializer;
		registerBasicAppenders();
	}

	private void registerBasicAppenders() {
		register(String.class, new StringAppender());
		register(Number.class, new NumberAppender());
		register(Boolean.class, new BooleanAppender());
		register(Calendar.class, new CalendarAppender());
		register(Date.class, new DateAppender());
		register(Character.class, new StringAppender());
		register(Enum.class, new StringAppender());
	}


	public void register(Class<?> clazz, Appender serializer) {
		serializers.put(clazz, serializer);
	}

	public Appender lookupAppenderFor(Object obj) {
		Class<?> objClass = obj.getClass();

		Appender serializer = serializers.get(objClass);
		if(serializer != null){
			return serializer;
		}

		for(Map.Entry<Class<?>,Appender> entry:serializers.entrySet()){
			if(entry.getKey().isAssignableFrom(objClass)){
				return entry.getValue();
			}
		}
		return defaultAppender;
	}

}