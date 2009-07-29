package org.merecode.json.registries;

import java.util.LinkedHashMap;
import java.util.Map;

import org.merecode.json.Appender;
import org.merecode.json.AppenderRegistry;


public class BasicInheritanceRegistry implements AppenderRegistry{

	private Map<Class<?>, Appender> appenders = new LinkedHashMap<Class<?>, Appender>();
	private Appender defaultAppender;

	public BasicInheritanceRegistry(Appender defaultAppender){
		this.defaultAppender = defaultAppender;
	}

	public void register(Class<?> clazz, Appender appender) {
		appenders.put(clazz, appender);
	}

	public Appender lookupAppenderFor(Object obj) {
		Class<?> objClass = obj.getClass();

		Appender appender = getRegisteredAppender(objClass);
		if(appender != null){
			return appender;
		}

		for(Map.Entry<Class<?>,Appender> entry:appenders.entrySet()){
			if(entry.getKey().isAssignableFrom(objClass)){
				return entry.getValue();
			}
		}
		return defaultAppender;
	}

	public Appender getRegisteredAppender(Class<?> clazz) {
		return appenders.get(clazz);
	}
}