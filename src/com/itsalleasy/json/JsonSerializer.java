package com.itsalleasy.json;

import java.io.IOException;
import java.io.Writer;

public interface JsonSerializer {

	public abstract String serialize(Object obj);

	public abstract void serialize(Object obj, Writer writer) throws IOException;

}