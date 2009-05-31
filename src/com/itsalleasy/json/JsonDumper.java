package com.itsalleasy.json;

import java.io.IOException;
import java.io.Writer;

import com.itsalleasy.walker.PropertyFilter;
import com.itsalleasy.walker.TrackingPolicy;
import com.itsalleasy.walker.Walker;

public class JsonDumper implements JsonSerializer {
	private AppenderRegistry serializerRegistry;
	private PropertyFilter filter;
	private TrackingPolicy trackingPolicy;
	
	public JsonDumper(){
	}

	public JsonDumper(AppenderRegistry registry){
		this(registry, null, null);
	}

	public JsonDumper(AppenderRegistry registry, PropertyFilter filter){
		this(registry, filter, null);
	}

	public JsonDumper(AppenderRegistry registry, PropertyFilter filter, TrackingPolicy trackingPolicy){
		this.serializerRegistry = registry;
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
		new Walker(new JsonWalkerVistor(writer, serializerRegistry), filter, trackingPolicy).walk(obj);
	}
}
