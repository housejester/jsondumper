/**
 * 
 */
package org.merecode.json.appenders;

import java.io.IOException;
import java.io.Writer;
import java.util.Date;

import org.merecode.json.Appender;


public class DateAppender implements Appender {
	public void append(Object obj, Writer writer) throws IOException {
		writer.append(""+((Date)obj).getTime());
	}
}