package org.merecode.json;

import java.io.IOException;
import java.io.Writer;

public class StringBuilderWriter extends Writer{
	private StringBuilder builder;
	public StringBuilderWriter(){
		this(new StringBuilder(128));
	}
	public StringBuilderWriter(StringBuilder builder){
		this.builder = builder;
	}
	public void close() throws IOException {
	}

	public void flush() throws IOException {
	}

	public Writer append(CharSequence csq) throws IOException {
		builder.append(csq);
		return this;
	}

	public Writer append(char c) throws IOException {
		builder.append(c);
		return this;
	}

	public Writer append(CharSequence csq, int start, int end)
			throws IOException {
		builder.append(csq, start, end);
		return this;
	}
	
	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {
		if(cbuf != null){
			builder.append(cbuf, off, len);
		}
	}
	
	public StringBuilder getBuilder(){
		return builder;
	}

	public String toString(){
		return builder.toString();
	}
}
