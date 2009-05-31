/**
 * 
 */
package com.itsalleasy.json.appenders;

import java.io.IOException;
import java.io.Writer;
import java.util.Date;

import com.itsalleasy.json.Appender;

public class DateAppender implements Appender {
	public void append(Object obj, Writer writer) throws IOException {
		writer.append("new Date(");
		writer.append(""+((Date)obj).getTime());
		writer.append(")");
	}
}