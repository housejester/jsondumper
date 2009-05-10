package com.itsalleasy.json;

import java.io.IOException;
import java.io.Writer;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

public class NetSfJsonTest extends JsonSerializerTest{
	@Override
	protected JsonSerializer createDumper() {
		return new NetSfJsonSerializer();
	}
}
class NetSfJsonSerializer implements JsonSerializer{
	private JSON json;
	public String serialize(Object obj) {
		json = JSONSerializer.toJSON(obj);
		return json.toString();
	}

	public void serialize(Object obj, Writer writer) throws IOException {
		writer.append(JSONSerializer.toJSON(obj).toString());
	}
	
}
