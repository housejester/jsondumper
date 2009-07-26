package org.merecode.json;

import java.io.IOException;
import java.io.Writer;
import net.sf.json.JSONSerializer;

public class NetSfJsonSerializer extends AbstractJsonSerializer{
	public void serialize(Object obj, Writer writer) throws IOException {
		writer.append(JSONSerializer.toJSON(obj).toString());
	}
}
