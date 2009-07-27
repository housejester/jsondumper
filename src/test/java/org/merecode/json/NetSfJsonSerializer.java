package org.merecode.json;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONSerializer;

public class NetSfJsonSerializer extends AbstractJsonSerializer{
	public void serialize(Object obj, Writer writer) throws IOException {
		obj = toValidRoot(obj);
		writer.append(JSONSerializer.toJSON(obj).toString());
	}
	private Object toValidRoot(Object obj){
		if(	obj instanceof String ||
			obj instanceof Number ||
			obj instanceof Boolean ||
			obj instanceof Enum || 
			obj instanceof Character 
		){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("value", obj);
			return map;
		}
		return obj;
	}
}
