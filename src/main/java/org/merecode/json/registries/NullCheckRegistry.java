package org.merecode.json.registries;

import org.merecode.json.Appender;
import org.merecode.json.AppenderRegistry;
import org.merecode.json.appenders.NullLiteralAppender;


public class NullCheckRegistry implements AppenderRegistry {
	private AppenderRegistry appenders;
	private static final Appender NULL_APPENDER = new NullLiteralAppender();
	public NullCheckRegistry(AppenderRegistry delegate){
		appenders = delegate;
	}
	public Appender lookupAppenderFor(Object obj) {
		if(obj == null){
			return NULL_APPENDER;
		}
		return appenders.lookupAppenderFor(obj);
	}

}
