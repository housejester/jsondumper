package com.itsalleasy.json;

import java.io.IOException;
import java.io.Writer;

public class JsonDumper {
	private SerializerRegistry serializerRegistry;
	private PropertyFilter filter;
	public JsonDumper(){
	}

	public JsonDumper(SerializerRegistry registry){
		this.serializerRegistry = registry;
	}

	public JsonDumper(SerializerRegistry registry, PropertyFilter filter){
		this.serializerRegistry = registry;
		this.filter = filter;
	}

	public String dump(Object obj) {
		StringBuilderWriter writer = new StringBuilderWriter();
		try {
			dump(obj, writer);
		} catch (IOException e) {
		}
		return writer.toString();
	}
	
	public void dump(Object obj, Writer writer) throws IOException{
		JsonWriter context = new JsonWriter(writer, serializerRegistry, filter);
		context.write(obj);
	}

}
