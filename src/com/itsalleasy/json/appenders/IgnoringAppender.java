/**
 * 
 */
package com.itsalleasy.json.appenders;

import java.io.IOException;
import java.io.Writer;

import com.itsalleasy.json.Appender;

public class IgnoringAppender implements Appender {
	public void append(Object obj, Writer writer) throws IOException {
	}
}