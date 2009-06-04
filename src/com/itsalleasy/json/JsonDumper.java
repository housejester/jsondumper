package com.itsalleasy.json;

import java.io.IOException;
import java.io.Writer;

import com.itsalleasy.json.registries.CachingAppenderRegistry;
import com.itsalleasy.json.registries.NullCheckRegistry;
import com.itsalleasy.walker.BasicNodeWalkerFactory;
import com.itsalleasy.walker.NodeWalkerFactory;
import com.itsalleasy.walker.PropertyFilter;
import com.itsalleasy.walker.PropertyFilters;
import com.itsalleasy.walker.TrackingPolicies;
import com.itsalleasy.walker.TrackingPolicy;
import com.itsalleasy.walker.Walker;

public class JsonDumper implements JsonSerializer {
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

	public String serialize(Object obj) {
		Writer writer = new StringBuilderWriter();
		try {
			serialize(obj, writer);
		} catch (IOException e) {
		}
		return writer.toString();
	}

	public void serialize(Object obj, Writer writer) throws IOException {
		try{
			walker.walk(obj, new JsonWalkerVisitor(writer, appenders));
		}catch(JsonIOException e){
			throw e.getIOException();
		}
	}
}
