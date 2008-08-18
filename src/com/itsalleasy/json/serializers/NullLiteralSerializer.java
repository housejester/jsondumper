/**
 * 
 */
package com.itsalleasy.json.serializers;

import java.io.IOException;

import com.itsalleasy.json.JsonWriter;
import com.itsalleasy.json.JsonSerializer;

public class NullLiteralSerializer implements JsonSerializer {
	public void toJson(Object obj, JsonWriter context) throws IOException {
		context.append("null");
	}
}