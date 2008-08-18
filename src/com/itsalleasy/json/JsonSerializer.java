/**
 * 
 */
package com.itsalleasy.json;

import java.io.IOException;

public interface JsonSerializer{
	public void toJson(Object obj, JsonWriter context) throws IOException;
}