/**
 * 
 */
package com.itsalleasy.json.serializers;

import java.io.IOException;
import java.io.Writer;
import java.util.Calendar;

public class CalendarSerializer extends DateSerializer {
	public void toJson(Object obj, Writer context) throws IOException {
		super.toJson(((Calendar)obj).getTime(), context);
	}
}