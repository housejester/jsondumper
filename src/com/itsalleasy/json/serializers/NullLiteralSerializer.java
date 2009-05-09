/**
 * 
 */
package com.itsalleasy.json.serializers;

import java.io.IOException;
import java.io.Writer;

import com.itsalleasy.json.JsonWriter;
import com.itsalleasy.json.JsonSerializeHandler;

public class NullLiteralSerializer implements JsonSerializeHandler {
	public void toJson(Object obj, Writer writer) throws IOException {
		writer.append("null");
	}
	public void toJson(Object obj, JsonWriter context) throws IOException {
		context.appendNullLiteral();
	}
}