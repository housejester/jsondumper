/**
 * 
 */
package org.merecode.json;

import java.io.IOException;
import java.io.Writer;

public interface Appender{
	public void append(Object obj, Writer writer) throws IOException;
}