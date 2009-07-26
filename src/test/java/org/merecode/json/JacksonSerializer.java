package org.merecode.json;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.introspect.BasicClassIntrospector;
import org.codehaus.jackson.map.introspect.JacksonAnnotationIntrospector;

public class JacksonSerializer  implements JsonSerializer{
	private static boolean written = false;
	public String serialize(Object obj) {
		StringWriter writer = new StringWriter();
		try {
			serialize(obj, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String value = writer.toString();
		if(!written){
			written = true;
			System.out.println(value);
		}
		return value;
	}

	public void serialize(Object obj, Writer writer) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(writer, obj);
	}

}
