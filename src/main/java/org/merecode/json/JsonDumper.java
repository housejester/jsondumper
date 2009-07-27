package org.merecode.json;

import java.io.IOException;
import java.io.Writer;

import org.merecode.json.appenders.StringAppender;
import org.merecode.json.registries.CachingAppenderRegistry;
import org.merecode.json.registries.NullCheckRegistry;
import org.merecode.walker.BasicNodeWalkerFactory;
import org.merecode.walker.NodeWalkerFactory;
import org.merecode.walker.PropertyFilter;
import org.merecode.walker.PropertyFilters;
import org.merecode.walker.TrackingPolicies;
import org.merecode.walker.TrackingPolicy;
import org.merecode.walker.Walker;

public class JsonDumper extends AbstractJsonSerializer {
	public static final AppenderRegistry DEFAULT_APPENDERS = new NullCheckRegistry(new CachingAppenderRegistry(new JsonWalkerAppenderRegistry()));
	public static final PropertyFilter DEFAULT_FILTER = PropertyFilters.IS_DEFAULT_OR_EMPTY;
	public static final TrackingPolicy DEFAULT_TRACKING_POLICY = TrackingPolicies.TRACK_OBJECTS_AND_PATHS;
	public static final NodeWalkerFactory DEFAULT_WALKER_FACTORY = new BasicNodeWalkerFactory();
	
	private Walker walker;
	private AppenderRegistry appenders;
	
	public JsonDumper(){
		this(null, null, null, null);
	}

	public JsonDumper(PropertyFilter filter, TrackingPolicy trackingPolicy, AppenderRegistry appenders, NodeWalkerFactory walkerFactory){
		this.appenders = appenders == null ? DEFAULT_APPENDERS : appenders;
		walker = new Walker(
				filter == null ? DEFAULT_FILTER : filter,
				trackingPolicy == null ? DEFAULT_TRACKING_POLICY : trackingPolicy,
				walkerFactory == null ? DEFAULT_WALKER_FACTORY : walkerFactory
		);
	}

	public void serialize(Object obj, Writer writer) throws IOException {
		try{
			walker.walk(obj, new JsonWalkerVisitor(writer, appenders));
		}catch(JsonIOException e){
			throw e.getIOException();
		}
	}

	public void setEscapeForwardSlashes(boolean escapeForwardSlashes) {
		Appender appender = appenders.lookupAppenderFor("foo");
		if(appender instanceof StringAppender){
			((StringAppender)appender).setEscapeForwardSlash(escapeForwardSlashes);
		}
	}
}
