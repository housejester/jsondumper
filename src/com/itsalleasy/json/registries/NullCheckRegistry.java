package com.itsalleasy.json.registries;

import com.itsalleasy.json.Appender;
import com.itsalleasy.json.AppenderRegistry;
import com.itsalleasy.json.appenders.NullLiteralAppender;

public class NullCheckRegistry implements AppenderRegistry {
	private AppenderRegistry registry;
	private static final Appender NULL_SERIALIZER = new NullLiteralAppender();
	public NullCheckRegistry(AppenderRegistry delegate){
		registry = delegate;
	}
	public Appender lookupSerializerFor(Object obj) {
		if(obj == null){
			return NULL_SERIALIZER;
		}
		return registry.lookupSerializerFor(obj);
	}

}
