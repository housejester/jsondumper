package com.itsalleasy.walker.json;

import java.io.IOException;
import java.io.Writer;

import com.itsalleasy.json.SerializerHandlerRegistry;
import com.itsalleasy.json.registries.BasicInheritanceRegistry;
import com.itsalleasy.json.registries.CachingSerializerRegistry;
import com.itsalleasy.walker.WalkerVisitor;

public class JsonProcessor implements WalkerVisitor{
	public static final SerializerHandlerRegistry DEFAULT_REGISTRY = new NullCheckRegistry(new CachingSerializerRegistry(new BasicInheritanceRegistry()));
	private Writer writer;
	private SerializerHandlerRegistry registry = DEFAULT_REGISTRY;
	
	public JsonProcessor(Writer writer){
		this(writer, DEFAULT_REGISTRY);
	}
	
	public JsonProcessor(Writer writer, SerializerHandlerRegistry registry) {
		this.writer = writer;
		this.registry = registry;
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
		registry = null;
	}

	public void visit(Object obj) {
		try {
			registry.lookupSerializerFor(obj).toJson(obj, writer);
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
