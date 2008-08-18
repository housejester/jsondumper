/**
 * 
 */
package com.itsalleasy.json.serializers;

import java.io.IOException;
import java.util.Calendar;

import com.itsalleasy.json.JsonWriter;

public class CalendarSerializer extends DateSerializer {

	public void toJson(Object obj, JsonWriter context) throws IOException {
		super.toJson(((Calendar)obj).getTime(), context);
	}
}