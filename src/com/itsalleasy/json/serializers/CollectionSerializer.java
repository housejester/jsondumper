/**
 * 
 */
package com.itsalleasy.json.serializers;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;

import com.itsalleasy.json.JsonWriter;
import com.itsalleasy.json.JsonSerializeHandler;

public class CollectionSerializer implements JsonSerializeHandler {
	public void toJson(Object obj, Writer writer) throws IOException {
		
	}
	@SuppressWarnings("unchecked")
	public void toJson(Object obj, JsonWriter context) throws IOException {
		context.beginArray(obj);
		int index = 0;
		for(Object value:((Collection<Object>)obj)){
			context.writeArrayItem(index, value);
			++index;
		}
		context.endArray();
	}
}