package com.itsalleasy.json.serializers;

import java.io.IOException;
import java.io.Writer;

import com.itsalleasy.json.JsonSerializeHandler;
import com.itsalleasy.json.JsonWriter;

public class ObjectReferenceSerializer implements JsonSerializeHandler{
	public void toJson(Object obj, Writer writer) throws IOException {
		writer.append("{\"$ref\":\"");
		writer.append(obj.toString());
		writer.append("\"}");
	}
	public void toJson(Object obj, JsonWriter context) throws IOException {
		context.append("{\"$ref\":\"");
		context.append(context.getPathRefToWritten(obj));
		context.append("\"}");
	}
}
