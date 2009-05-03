/**
 * 
 */
package com.itsalleasy.json.serializers;

import java.io.IOException;
import java.util.Collection;

import com.itsalleasy.json.JsonWriter;
import com.itsalleasy.json.JsonSerializer;

public class CollectionSerializer implements JsonSerializer {
	@SuppressWarnings("unchecked")
	public void toJson(Object obj, JsonWriter context) throws IOException {
		context.beginArray(obj);
		int index = 0;
		for(Object value:((Collection<Object>)obj)){
			context.writeArrayItem(index, value, index>0);
			++index;
		}
		context.endArray();
	}
}