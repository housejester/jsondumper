package com.itsalleasy.json;

import java.io.IOException;
import java.io.Writer;

import com.itsalleasy.json.registries.CachingAppenderRegistry;
import com.itsalleasy.json.registries.NullCheckRegistry;
import com.itsalleasy.walker.PropertyFilter;
import com.itsalleasy.walker.PropertyFilters;
import com.itsalleasy.walker.TrackingPolicies;
import com.itsalleasy.walker.TrackingPolicy;
import com.itsalleasy.walker.Walker;

public class JsonDumper implements JsonSerializer {
	public static final AppenderRegistry DEFAULT_APPENDERS = new NullCheckRegistry(new CachingAppenderRegistry(new JsonWalkerAppenderRegistry()));
	public static final PropertyFilter DEFAULT_FILTER = PropertyFilters.IS_DEFAULT_OR_EMPTY;
	public static final TrackingPolicy DEFAULT_TRACKING_POLICY = TrackingPolicies.TRACK_OBJECTS_AND_PATHS;
	
	private PropertyFilter filter;
	private TrackingPolicy trackingPolicy;
	private AppenderRegistry appenders;
	
	public JsonDumper(){
		this(null, null, null);
	}

	public JsonDumper(PropertyFilter filter, TrackingPolicy trackingPolicy, AppenderRegistry appenders){
		this.filter = filter == null ? DEFAULT_FILTER : filter;
		this.trackingPolicy = trackingPolicy == null ? DEFAULT_TRACKING_POLICY : trackingPolicy;
		this.appenders = appenders == null ? DEFAULT_APPENDERS : appenders;
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
			new Walker(filter, trackingPolicy).walk(obj, new JsonWalkerVisitor(writer, appenders));
		}catch(JsonIOException e){
			throw e.getIOException();
		}
	}
}
