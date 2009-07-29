package org.merecode.json;

import java.io.IOException;
import java.io.Writer;

public interface JsonSerializer {
	String serialize(Object obj);
	void serialize(Object obj, Writer writer) throws IOException;
	void setEscapeForwardSlashes(boolean escapeForwardSlashes);
	void setEscapeNonAsciiRange(boolean escapeNonAsciiRange);
	void setMaxDecimalDigits(int i);
}