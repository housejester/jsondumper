package com.itsalleasy.json;

import java.io.IOException;
import java.io.Writer;

import com.itsalleasy.walker.WalkerVisitor;

public class JsonWalkerVisitor implements WalkerVisitor{
	private AppenderRegistry appenders;
	private Writer writer;
	
	public JsonWalkerVisitor(Writer writer, AppenderRegistry appenders){
		this.writer = writer;
		this.appenders = appenders;
	}

	public void startWalk(Object object) {
	}
	
	public void endWalk(Object object) {
		try {
			writer.flush();
		} catch (IOException e) {
			throw new JsonIOException(e);
		} finally {
			writer = null;
		}
	}

	public void visit(Object obj) {
		try {
			appenders.lookupAppenderFor(obj).append(obj, writer);
		} catch (IOException e) {
			throw new JsonIOException(e);
		}
	}

	public void revisit(Object obj, String path) {
		print("{\"$ref\":\"");
		print(path);
		print("\"}");
	}


	public void beanStart(Object obj) {
		print('{');
	}

	public void beanEnd(Object obj) {
		print('}');
	}

	public void beanProperty(String name, Object value, boolean isFirst) {
		if(!isFirst){
			print(',');
		}
		print('"');
		print(name);
		print('"');
		print(':');
	}

	public void arrayStart(Object obj) {
		print('[');
	}

	public void arrayEnd(Object obj) {
		print(']');
	}

	public void arrayItem(int index, Object item) {
		if(index!=0){
			print(',');
		}
	}

	private final void print(char c){
		try {
			writer.append(c);
		} catch (IOException e) {
			throw new JsonIOException(e);
		}
	}
	private final void print(String s){
		try {
			writer.append(s);
		} catch (IOException e) {
			throw new JsonIOException(e);
		}
	}
}
