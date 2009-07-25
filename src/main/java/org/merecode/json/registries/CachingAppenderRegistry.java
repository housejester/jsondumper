package org.merecode.json.registries;

import java.util.HashMap;
import java.util.Map;

import org.merecode.json.Appender;
import org.merecode.json.AppenderRegistry;


public class CachingAppenderRegistry implements AppenderRegistry{
	private Map<Class<?>, Appender> cache;
	private AppenderRegistry target;

	public CachingAppenderRegistry(AppenderRegistry target){
		cache = new HashMap<Class<?>, Appender>();
		this.target = target;
	}
	public Appender lookupAppenderFor(Object obj) {
		Class<?> objClass = obj.getClass();
		Appender serializer = cache.get(objClass);
		if(serializer == null){
			serializer = target.lookupAppenderFor(obj);
			cache.put(objClass, serializer);
		}
		return serializer;
	}

	protected AppenderRegistry getTargetRegistry(){
		return target;
	}

}
