package com.itsalleasy.json.serializers;

import java.io.IOException;
import java.io.Writer;

import com.itsalleasy.json.JsonSerializeHandler;

public abstract class NameValuePairsSerializer implements JsonSerializeHandler{
	public void toJson(Object obj, Writer writer) throws IOException {
	}
	
	protected abstract Iterable<NameValuePair> getNameValuePairs(Object obj);
}
