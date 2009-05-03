package com.itsalleasy.json;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.itsalleasy.json.registries.CircularReferenceSupportingRegistry;
import com.itsalleasy.json.serializers.NullLiteralSerializer;

public class JsonWriter {
	private static final Integer ZERO = new Integer(0);
	private static final JsonSerializer NULL_SERIALIZER = new NullLiteralSerializer();
	private Map<Object, String> written;
	private Writer writer;
	SerializerRegistry serializerRegistry;
	StringBuilder path = new StringBuilder();
	private Object currentPathItem;

	public JsonWriter(Writer writer, SerializerRegistry serializerRegistry) {
		this.writer = writer;
		this.serializerRegistry = new CircularReferenceSupportingRegistry(this, serializerRegistry);
		written = new HashMap<Object, String>();
	}
	public void write(Object root) throws IOException{
		write("",root);
	}
	public boolean writeProperty(String pathAsPropKey, Object obj, boolean prefixWithComma) throws IOException{
		if( shouldFilterOut(pathAsPropKey, obj)){
			return false;
		}

		if(prefixWithComma){
			append(",");
		}
		if(!"".equals(pathAsPropKey)){
			append('"');
			append(pathAsPropKey);
			append('"');
			append(':');
		}

		write(pathAsPropKey,obj);
		return true;
	}
	public String getPathForWritten(Object obj){
		return written.get(obj);
	}
	public void writeArrayItem(Integer pathAsArrayIndex, Object obj, boolean prefixWithComma) throws IOException{
		if(prefixWithComma){
			append(',');
		}
		write(pathAsArrayIndex, obj);
	}
	protected void write(Object pathItem, Object obj) throws IOException{
		currentPathItem = pathItem;
		findSerializerFor(obj).toJson(obj, this);
	}
	protected JsonSerializer findSerializerFor(Object obj){
		return obj == null ? NULL_SERIALIZER : serializerRegistry.lookupSerializerFor(obj);
	}
	public void append(String string) throws IOException {
		writer.append(string);
	}		
	public void append(char c) throws IOException {
		writer.append(c);
	}
	public void appendNullLiteral() throws IOException{
		append("null");
	}
	public Writer getWriter() {
		return writer;
	}
	public boolean shouldFilterOut(String name, Object value) {
		return 	value == null || Boolean.FALSE.equals(value) || "".equals(value) 
				|| ZERO.equals(value) || isEmptyCollection(value) || isEmptyMap(value);
	}
	@SuppressWarnings("unchecked")
	private boolean isEmptyCollection(Object value){
		return (value instanceof Collection) && ((Collection)value).isEmpty();
	}
	@SuppressWarnings("unchecked")
	private boolean isEmptyMap(Object value){
		return (value instanceof Map) && ((Map)value).isEmpty();
	}
	public void beginArray(Object array) throws IOException {
		begin('[', array);
	}
	public void endArray() throws IOException {
		end(']');
	}
	public void beginObject(Object obj) throws IOException {
		begin('{', obj);
	}
	public void endObject() throws IOException {
		end('}');
	}
	protected void begin(char delim, Object obj) throws IOException{
		pushPath(currentPathItem);
		written.put(obj, path.toString());
		writer.append(delim);
	}
	protected void end(char delim) throws IOException{
		writer.append(delim);
		popPath();
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
}
