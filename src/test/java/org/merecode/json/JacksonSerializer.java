package org.merecode.json;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.codehaus.jackson.map.ObjectMapper;

public class JacksonSerializer  implements JsonSerializer{
	public String serialize(Object obj) {
		StringWriter writer = new StringWriter();
		try {
			serialize(obj, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return writer.toString();
	}

	public void serialize(Object obj, Writer writer) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(writer, obj);
	}

}
