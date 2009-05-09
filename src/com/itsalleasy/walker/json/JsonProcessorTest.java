package com.itsalleasy.walker.json;

import java.io.IOException;
import java.io.Writer;

import com.itsalleasy.json.JsonSerializer;
import com.itsalleasy.json.JsonSerializerTest;
import com.itsalleasy.json.StringBuilderWriter;
import com.itsalleasy.walker.Walker;

public class JsonProcessorTest extends JsonSerializerTest{
	public void setUp(){
		dumper = new JsonSerializer(){
			public String serialize(Object obj) {
				StringBuilderWriter writer = new StringBuilderWriter();
				try {
					serialize(obj, writer);
				} catch (IOException e) {
				}
				return writer.toString();
			}

			public void serialize(Object obj, Writer writer) throws IOException {
				JsonProcessor processor = new JsonProcessor(writer);
				Walker walker = new Walker(processor);
				walker.walk(obj);
			}
		};
	}
}
