package org.merecode.json;

import org.merecode.json.JsonSerializer;
import org.testng.annotations.Test;


@Test
public class NetSfJsonTest extends JsonSerializerTest{
	@Override
	protected JsonSerializer createDumper() {
		return new NetSfJsonSerializer();
	}

}
