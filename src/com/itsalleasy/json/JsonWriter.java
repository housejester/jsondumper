package com.itsalleasy.json;

import java.io.IOException;
import java.io.Writer;
import java.util.IdentityHashMap;
import java.util.Map;

import com.itsalleasy.json.registries.BasicInheritanceRegistry;
import com.itsalleasy.json.registries.CachingSerializerRegistry;
import com.itsalleasy.json.serializers.NullLiteralSerializer;
import com.itsalleasy.json.serializers.ObjectReferenceSerializer;

public class JsonWriter {
	private static final JsonSerializeHandler NULL_SERIALIZER = new NullLiteralSerializer();
	private static final JsonSerializeHandler OBJECT_REF_SERIALIZER = new ObjectReferenceSerializer();
	private static final SerializerHandlerRegistry DEFAULT_SERIALIZER_REGISTRY = new CachingSerializerRegistry(new BasicInheritanceRegistry()); 
		
	private Map<Object, String> written;
	private PropertyFilter filter;
	private Writer writer;
	SerializerHandlerRegistry serializerRegistry;
	StringBuilder path = new StringBuilder();
	private Object currentPathItem;

	public JsonWriter(Writer writer){
		this(writer, DEFAULT_SERIALIZER_REGISTRY, PropertyFilters.IS_DEFAULT_OR_EMPTY);
	}
	public JsonWriter(Writer writer, SerializerHandlerRegistry serializerRegistry, PropertyFilter filter) {
		this.writer = writer;
		this.serializerRegistry = serializerRegistry == null ? DEFAULT_SERIALIZER_REGISTRY : serializerRegistry;
		this.filter = filter == null ? PropertyFilters.IS_DEFAULT_OR_EMPTY : filter;
		written = new IdentityHashMap<Object, String>();
	}
	public void write(Object root) throws IOException{
		write("",root);
	}
	public boolean writeProperty(String pathAsPropKey, Object obj, boolean isFirst) throws IOException{
		if( filter.filter(obj, pathAsPropKey) ){
			return false;
		}

		beginProperty(pathAsPropKey, obj, isFirst);
		write(pathAsPropKey,obj);
		endProperty(pathAsPropKey, obj);
		return true;
	}
	public void writeArrayItem(Integer pathAsArrayIndex, Object obj) throws IOException{
		beginArrayItem(pathAsArrayIndex, obj);
		write(pathAsArrayIndex, obj);
		endArrayItem(pathAsArrayIndex, obj);
	}
	private void write(Object pathItem, Object obj) throws IOException{
		currentPathItem = pathItem;
		findSerializerFor(obj).toJson(obj, this);
	}
	private JsonSerializeHandler findSerializerFor(Object obj){
		if(obj == null){
			return NULL_SERIALIZER;
		}

		if(written.containsKey(obj)){
			return OBJECT_REF_SERIALIZER;
		}

		return serializerRegistry.lookupSerializerFor(obj);		
	}
	public String getPathRefToWritten(Object obj){
		return written.get(obj);
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
	public void beginArrayItem(Integer index, Object value) throws IOException{
		if(index > 0){
			append(',');
		}
	}
	public void beginProperty(String key, Object value, boolean isFirstProperty) throws IOException{
		if(!isFirstProperty){
			append(",");
		}
		append('"');
		append(key);
		append('"');
		append(':');
	}
	public void endProperty(String key, Object value) throws IOException{
	}
	public void endArrayItem(Integer index, Object value) throws IOException{
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
	private void begin(char delim, Object obj) throws IOException{
		pushPath(currentPathItem);
		written.put(obj, path.toString());
		writer.append(delim);
	}
	private void end(char delim) throws IOException{
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
