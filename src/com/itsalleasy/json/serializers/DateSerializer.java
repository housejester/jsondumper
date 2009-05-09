/**
 * 
 */
package com.itsalleasy.json.serializers;

import java.io.IOException;
import java.io.Writer;
import java.util.Date;

import com.itsalleasy.json.JsonWriter;
import com.itsalleasy.json.JsonSerializeHandler;

public class DateSerializer implements JsonSerializeHandler {
	public void toJson(Object obj, Writer writer) throws IOException {
		writer.append("new Date(");
		writer.append(""+((Date)obj).getTime());
		writer.append(")");
	}
	public void toJson(Object obj, JsonWriter context) throws IOException {
		context.append("new Date(");
		context.append(""+((Date)obj).getTime());
		context.append(")");
	}
}