/**
 * 
 */
package com.itsalleasy.json.serializers;

import java.io.IOException;

import org.apache.commons.lang.StringEscapeUtils;

import com.itsalleasy.json.JsonWriter;
import com.itsalleasy.json.JsonSerializer;

public class StringSerializer implements JsonSerializer {
	public void toJson(Object obj, JsonWriter context) throws IOException {
		context.append('"');
		StringEscapeUtils.escapeJavaScript(context.getWriter(), obj.toString());
		context.append('"');
	}
}