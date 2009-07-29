package org.merecode.json.appenders;

import java.io.IOException;
import java.io.Writer;

import org.merecode.json.Appender;

public class RawNumberAppender implements Appender{
	public void append(Object obj, Writer writer) throws IOException {
		writer.write(String.valueOf(obj));
	}
}
