package org.merecode.json;

import java.io.IOException;
import java.io.Writer;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

public class XStreamJsonSerializer extends AbstractJsonSerializer{
	public void serialize(Object obj, Writer writer) throws IOException {
		XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
		    public HierarchicalStreamWriter createWriter(Writer writer) {
		        return new JsonWriter(writer, new char[0], "", JsonWriter.DROP_ROOT_MODE);
		    }
		});
		xstream.toXML(obj, writer);
	}
}
