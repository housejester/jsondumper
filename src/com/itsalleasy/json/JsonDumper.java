package com.itsalleasy.json;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import com.itsalleasy.json.registries.BasicSerializerRegistry;
import com.itsalleasy.json.registries.CachingSerializerRegistry;

public class JsonDumper {
	private SerializerRegistry serializerRegistry;
	public JsonDumper(){
		this(new CachingSerializerRegistry(new BasicSerializerRegistry()));
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
		JsonWriter context = new JsonWriter(writer, serializerRegistry, obj);
		context.write();
	}

}
