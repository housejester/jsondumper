/**
 * 
 */
package com.itsalleasy.json.serializers;

import java.io.IOException;
import java.util.Arrays;

import com.itsalleasy.json.JsonWriter;

public class ArraySerializer extends CollectionSerializer {
	public void toJson(Object obj, JsonWriter context) throws IOException {
		super.toJson(Arrays.asList(((Object[])obj)), context);
	}
}