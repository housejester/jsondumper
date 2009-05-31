package com.itsalleasy.json;

import java.io.IOException;
import java.io.Writer;

import com.itsalleasy.json.registries.BasicInheritanceRegistry;
import com.itsalleasy.json.registries.CachingAppenderRegistry;
import com.itsalleasy.json.registries.NullCheckRegistry;
import com.itsalleasy.walker.WalkerVisitor;

public class JsonWalkerVistor implements WalkerVisitor{
	public static final AppenderRegistry DEFAULT_APPENDER_REGISTRY = new NullCheckRegistry(new CachingAppenderRegistry(new BasicInheritanceRegistry()));
	private Writer writer;
	private AppenderRegistry appenders;
	
	public JsonWalkerVistor(Writer writer){
		this(writer, DEFAULT_APPENDER_REGISTRY);
	}
	
	public JsonWalkerVistor(Writer writer, AppenderRegistry appenders) {
		this.writer = writer;
		this.appenders = appenders == null ? DEFAULT_APPENDER_REGISTRY : appenders;
	}

	public void startWalk(Object object) {
	}
	
	public void endWalk(Object object) {
		try {
			writer.flush();
		} catch (IOException e) {
		}
		try {
			writer.close();
		} catch (IOException e) {
		}
		writer = null;
		appenders = null;
	}

	public void visit(Object obj) {
		try {
			appenders.lookupAppenderFor(obj).append(obj, writer);
		} catch (IOException e) {
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
		}
	}
	private final void print(String s){
		try {
			writer.append(s);
		} catch (IOException e) {
		}
	}

}
