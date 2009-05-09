/**
 * 
 */
package com.itsalleasy.json.serializers;

import java.io.IOException;
import java.io.Writer;

import com.itsalleasy.json.JsonWriter;
import com.itsalleasy.json.JsonSerializeHandler;

public class BooleanSerializer implements JsonSerializeHandler {
	public void toJson(Object obj, Writer writer) throws IOException {
		writer.write(obj.toString());
	}
	public void toJson(Object obj, JsonWriter context) throws IOException {
		context.append(obj.toString());
	}
}