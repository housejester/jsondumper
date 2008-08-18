package com.itsalleasy.json.serializers;

import java.io.IOException;

import com.itsalleasy.json.JsonSerializer;
import com.itsalleasy.json.JsonWriter;

public class ObjectReferenceSerializer implements JsonSerializer{
	private String path;
	public ObjectReferenceSerializer(String path) {
		this.path = path;
	}

	public void toJson(Object obj, JsonWriter context) throws IOException {
		context.append("{\"$ref\":\"");
		context.append(path);
		context.append("\"}");
	}
}
