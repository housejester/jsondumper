package com.itsalleasy.json.appenders;

import java.io.IOException;
import java.io.Writer;

import com.itsalleasy.json.Appender;

public class ObjectReferenceAppender implements Appender{
	public void append(Object obj, Writer writer) throws IOException {
		writer.append("{\"$ref\":\"");
		writer.append(obj.toString());
		writer.append("\"}");
	}
}
