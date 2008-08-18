package com.itsalleasy.json.serializers;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import com.itsalleasy.json.JsonWriter;
import com.itsalleasy.json.JsonSerializer;

public abstract class NameValuePairsSerializer implements JsonSerializer, ReferenceableSerializer{
	private static final Number ZERO = 0;
	public void toJson(Object obj, JsonWriter context)
			throws IOException {
		Iterable<NameValuePair> nameValuePairs = getNameValuePairs(obj);

		context.append('{');
		boolean firstPropertyDumped = false;
		for(NameValuePair nameValuePair:nameValuePairs){
			if(shouldNotDump(nameValuePair)){
				continue;
			}
			if(firstPropertyDumped){
				context.append(',');
			}else{
				firstPropertyDumped = true;
			}
			context.writeAsProperty(nameValuePair.getName(), nameValuePair.getValue());
		}
		context.append('}');
	}
	protected boolean shouldNotDump(NameValuePair pair){
		Object value = pair.getValue();
		return 	value == null || Boolean.FALSE.equals(value) || "".equals(value) 
				|| ZERO.equals(value) || isEmptyCollection(value) || isEmptyMap(value);
	}
	@SuppressWarnings("unchecked")
	private boolean isEmptyCollection(Object value){
		return (value instanceof Collection) && ((Collection)value).isEmpty();
	}
	@SuppressWarnings("unchecked")
	private boolean isEmptyMap(Object value){
		return (value instanceof Map) && ((Map)value).isEmpty();
	}
	protected abstract Iterable<NameValuePair> getNameValuePairs(Object obj);
}
