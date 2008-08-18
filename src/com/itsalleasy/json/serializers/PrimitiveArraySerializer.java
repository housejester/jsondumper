package com.itsalleasy.json.serializers;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;

import com.itsalleasy.json.JsonWriter;

public class PrimitiveArraySerializer extends CollectionSerializer{

	public Object[] toWrappedArray(Object arr){
        int length = Array.getLength(arr);
        Object newarr[] = new Object[length];

        for (int i = 0; i < length; i++) {
            newarr[i] = Array.get(arr, i);
        }

        return newarr;
    }
	public void toJson(Object obj, JsonWriter context) throws IOException {
		super.toJson(Arrays.asList(toWrappedArray(obj)), context);
	}

}
