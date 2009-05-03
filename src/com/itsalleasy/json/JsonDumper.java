package com.itsalleasy.json;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

public class JsonDumper {
	private SerializerRegistry serializerRegistry;
	public JsonDumper(){
		this(null);
	}

	public JsonDumper(SerializerRegistry registry){
		this.serializerRegistry = registry;
	}

	public String dump(Object obj) {
		StringWriter writer = new StringWriter();
		try {
			dump(obj, writer);
		} catch (IOException e) {
		}
		return writer.toString();
	}
	
	public void dump(Object obj, Writer writer) throws IOException{
		JsonWriter context = new JsonWriter(writer, serializerRegistry);
		context.write(obj);
	}

}
