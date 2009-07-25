/**
 * 
 */
package com.itsalleasy.json.appenders;

import java.io.IOException;
import java.io.Writer;
import java.util.Calendar;

public class CalendarAppender extends DateAppender {
	public void append(Object obj, Writer context) throws IOException {
		super.append(((Calendar)obj).getTime(), context);
	}
}