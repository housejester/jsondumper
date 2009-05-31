package com.itsalleasy.walker.json;

import java.io.IOException;
import java.io.Writer;

import com.itsalleasy.json.JsonSerializer;
import com.itsalleasy.json.StringBuilderWriter;
import com.itsalleasy.walker.Walker;

public class JsonWalkerSerializer implements JsonSerializer{
	private Walker walker;
	public String serialize(Object obj) {
		Writer writer = new StringBuilderWriter();
		try {
			serialize(obj, writer);
		} catch (IOException e) {
		}
		return writer.toString();
	}

	public void serialize(Object obj, Writer writer) throws IOException {
		JsonProcessor processor = new JsonProcessor(writer);
		walker = new Walker(processor);
		walker.walk(obj);
	}


	public Walker getWalker() {
		return walker;
	}

	public void setWalker(Walker walker) {
		this.walker = walker;
	}
	
	
}
