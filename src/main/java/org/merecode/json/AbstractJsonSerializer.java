package org.merecode.json;

import java.io.IOException;
import java.io.Writer;

public abstract class AbstractJsonSerializer implements JsonSerializer{
	public String serialize(Object obj) {
		Writer writer = new StringBuilderWriter();
		try {
			serialize(obj, writer);
		} catch (IOException e) {
		}
		return writer.toString();
	}

	public void setEscapeForwardSlashes(boolean escapeForwardSlashes) {
	}

	public void setEscapeNonAsciiRange(boolean escapeNonAsciiRange) {
	}

	public void setMaxDecimalDigits(int i) {
	}

	public void setMinDecimalDigits(int minDigits) {
	}

	public void setSkipNulls(boolean skip) {
	}

	public void setSkipEmptyCollections(boolean skip) {
	}
	
}
