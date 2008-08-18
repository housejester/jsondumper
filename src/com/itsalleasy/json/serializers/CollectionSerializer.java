/**
 * 
 */
package com.itsalleasy.json.serializers;

import java.io.IOException;
import java.util.Collection;

import com.itsalleasy.json.JsonWriter;
import com.itsalleasy.json.JsonSerializer;

public class CollectionSerializer implements JsonSerializer,ReferenceableSerializer {
	@SuppressWarnings("unchecked")
	public void toJson(Object obj, JsonWriter context) throws IOException {
		context.append('[');
		int index = 0;
		for(Object value:((Collection<Object>)obj)){
			if(index > 0){
				context.append(',');
			}
			context.writeAsArrayItem(index, value);
			++index;
		}
		context.append(']');
	}
}