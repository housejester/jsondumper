package com.itsalleasy.json.serializers;

import java.io.IOException;

import com.itsalleasy.json.JsonSerializer;
import com.itsalleasy.json.JsonWriter;

public class ObjectReferenceSerializer implements JsonSerializer{
	public void toJson(Object obj, JsonWriter context) throws IOException {
		context.append("{\"$ref\":\"");
		context.append(context.getPathRefToWritten(obj));
		context.append("\"}");
	}
}
