/**
 * 
 */
package com.itsalleasy.json.serializers;

import java.io.IOException;
import java.util.Date;

import com.itsalleasy.json.JsonWriter;
import com.itsalleasy.json.JsonSerializer;

public class DateSerializer implements JsonSerializer {
	public void toJson(Object obj, JsonWriter context) throws IOException {
		context.append("new Date(");
		context.append(""+((Date)obj).getTime());
		context.append(")");
	}
}