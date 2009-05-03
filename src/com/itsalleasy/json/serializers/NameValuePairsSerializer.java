package com.itsalleasy.json.serializers;

import java.io.IOException;

import com.itsalleasy.json.JsonWriter;
import com.itsalleasy.json.JsonSerializer;

public abstract class NameValuePairsSerializer implements JsonSerializer{
	public void toJson(Object obj, JsonWriter context)
			throws IOException {
		Iterable<NameValuePair> nameValuePairs = getNameValuePairs(obj);

		context.beginObject(obj);
		boolean firstPropertyDumped = false;
		for(NameValuePair nameValuePair:nameValuePairs){
			firstPropertyDumped = context.writeProperty(nameValuePair.getName(), nameValuePair.getValue(), firstPropertyDumped) || firstPropertyDumped;
		}
		context.endObject();
	}
	protected abstract Iterable<NameValuePair> getNameValuePairs(Object obj);
}
