package org.merecode.json.registries;

import org.merecode.json.Appender;
import org.merecode.json.AppenderRegistry;

public class CombinedAppenderRegistry implements AppenderRegistry {
	private AppenderRegistry[] registries;
	public CombinedAppenderRegistry(AppenderRegistry...registries){
		this.registries = registries;
	}
	public Appender lookupAppenderFor(Object obj) {
		for(AppenderRegistry registry : registries){
			Appender appender = registry.lookupAppenderFor(obj);
			if(appender != null){
				return appender;
			}
		}
		return null;
	}

}
