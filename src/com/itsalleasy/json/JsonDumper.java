package com.itsalleasy.json;

import java.io.IOException;
import java.io.Writer;

public class JsonDumper implements JsonSerializer {
	private SerializerHandlerRegistry serializerRegistry;
	private PropertyFilter filter;
	private JsonWriter context;
	public JsonDumper(){
	}

	public JsonDumper(SerializerHandlerRegistry registry){
		this.serializerRegistry = registry;
	}

	public JsonDumper(SerializerHandlerRegistry registry, PropertyFilter filter){
		this.serializerRegistry = registry;
		this.filter = filter;
	}

	public String serialize(Object obj) {
		StringBuilderWriter writer = new StringBuilderWriter();
		try {
			serialize(obj, writer);
		} catch (IOException e) {
		}
		return writer.toString();
	}
	
	public void serialize(Object obj, Writer writer) throws IOException{
		context = new JsonWriter(writer, serializerRegistry, filter);
		context.write(obj);
	}

	public SerializerHandlerRegistry getSerializerRegistry() {
		return serializerRegistry;
	}

	public void setSerializerRegistry(SerializerHandlerRegistry serializerRegistry) {
		this.serializerRegistry = serializerRegistry;
	}

	public PropertyFilter getFilter() {
		return filter;
	}

	public void setFilter(PropertyFilter filter) {
		this.filter = filter;
	}

	public JsonWriter getContext() {
		return context;
	}

	public void setContext(JsonWriter context) {
		this.context = context;
	}
	
	

}
