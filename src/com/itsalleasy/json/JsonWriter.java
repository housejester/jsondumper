package com.itsalleasy.json;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import com.itsalleasy.json.serializers.NullLiteralSerializer;
import com.itsalleasy.json.serializers.ObjectReferenceSerializer;
import com.itsalleasy.json.serializers.ReferenceableSerializer;

public class JsonWriter {
	private Map<Object, String> written;
	private Writer writer;
	private Object root;
	SerializerRegistry serializerRegistry;
	StringBuilder path = new StringBuilder();

	public JsonWriter(Writer writer, SerializerRegistry serializerRegistry, Object root) {
		this.writer = writer;
		this.serializerRegistry = serializerRegistry;
		this.root = root;
		written = new HashMap<Object, String>();
	}
	public void write() throws IOException{
		write((Object)"",root);
	}
	public void writeAsProperty(String pathAsPropKey, Object obj) throws IOException{
		if(!"".equals(pathAsPropKey)){
			append('"');
			append(pathAsPropKey);
			append('"');
			append(':');
		}
		write(pathAsPropKey,obj);
	}
	public void writeAsArrayItem(Integer pathAsArrayIndex, Object obj) throws IOException{
		write(pathAsArrayIndex, obj);
	}
	protected void write(Object pathItem, Object obj) throws IOException{
		findSerializerFor(pathItem, obj).toJson(obj, this);
	}
	protected JsonSerializer findSerializerFor(Object pathItem, Object obj){
		if(obj == null){
			return new NullLiteralSerializer();
		}

		JsonSerializer serializer = serializerRegistry.lookupSerializerFor(obj.getClass());

		if(!(serializer instanceof ReferenceableSerializer)){
			return serializer;
		}

		String path = written.get(obj);
		if(path!=null){
			return new ObjectReferenceSerializer(path);
		}

		return new PathAwareSerializer(pathItem, serializer);
	}
	private void pushPath(Object pathItem){
		if(path.length() > 0){
			path.append('.');
		}
		path.append(pathItem);
	}
	private void popPath(){
		int index = path.lastIndexOf(".");
		if(index == -1){
			path = new StringBuilder();
		}else{
			path.delete(index, path.length());
		}
	}
	public void append(String string) throws IOException {
		writer.append(string);
	}		
	public void append(char c) throws IOException {
		writer.append(c);
	}
	public Writer getWriter() {
		return writer;
	}
	class PathAwareSerializer implements JsonSerializer{ 
		private Object pathItem;
		private JsonSerializer serializer;

		PathAwareSerializer(Object pathItem, JsonSerializer serializer){
			this.pathItem = pathItem;
			this.serializer = serializer;
		}
		public void toJson(Object obj, JsonWriter context) throws IOException {
			pushPath(pathItem);
			written.put(obj, path.toString());
			serializer.toJson(obj, context);
			popPath();
		}
	}
}
