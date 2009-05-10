package com.itsalleasy.walker.json;

import java.io.IOException;
import java.io.Writer;

import com.itsalleasy.json.SerializerHandlerRegistry;
import com.itsalleasy.json.registries.BasicInheritanceRegistry;
import com.itsalleasy.json.registries.CachingSerializerRegistry;
import com.itsalleasy.walker.WalkerVisitor;

public class JsonProcessor implements WalkerVisitor{
	private Writer writer;
	private SerializerHandlerRegistry registry;
	
	public JsonProcessor(Writer writer){
		this.writer = writer;
		this.registry = new NullCheckRegistry(new CachingSerializerRegistry(new BasicInheritanceRegistry()));
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
	
	public void arrayStart(Object obj) {
		print('[');
	}

	public void arrayEnd(Object obj) {
		print(']');
	}

	public void revisit(Object obj, String path) {
		print("{\"$ref\":\"");
		print(path);
		print("\"}");
	}

	public void visit(Object obj) {
		try {
			registry.lookupSerializerFor(obj).toJson(obj, writer);
		} catch (IOException e) {
		}
	}

	public void arrayItem(Object item, int i) {
		if(i!=0){
			print(',');
		}
	}

	public void beanEnd(Object obj) {
		print('}');
	}

	public void beanProperty(Object value, String name, boolean isFirst) {
		if(!isFirst){
			print(',');
		}
		print('"');
		print(name);
		print('"');
		print(':');
	}

	public void beanStart(Object obj) {
		print('{');
	}

}
