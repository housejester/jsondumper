package com.itsalleasy.json;

import java.io.IOException;
import java.io.Writer;

import com.itsalleasy.walker.PropertyFilter;
import com.itsalleasy.walker.PropertyFilters;
import com.itsalleasy.walker.TrackingPolicies;
import com.itsalleasy.walker.TrackingPolicy;
import com.itsalleasy.walker.Walker;

public class JsonDumper implements JsonSerializer {
	private PropertyFilter filter;
	private TrackingPolicy trackingPolicy;
	
	public JsonDumper(){
		this(PropertyFilters.IS_DEFAULT_OR_EMPTY, TrackingPolicies.TRACK_OBJECTS_AND_PATHS);
	}

	public JsonDumper(PropertyFilter filter, TrackingPolicy trackingPolicy){
		this.filter = filter;
		this.trackingPolicy = trackingPolicy;
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
			new Walker(new JsonWalkerVisitor(writer), filter, trackingPolicy.createTracker()).walk(obj);
		}catch(JsonIOException e){
			throw e.getIOException();
		}
	}
}
