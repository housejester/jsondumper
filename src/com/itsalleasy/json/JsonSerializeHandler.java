/**
 * 
 */
package com.itsalleasy.json;

import java.io.IOException;
import java.io.Writer;

public interface JsonSerializeHandler{
	public void toJson(Object obj, Writer context) throws IOException;
}