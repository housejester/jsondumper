package org.merecode.json;

import org.merecode.json.JsonSerializer;
import org.testng.annotations.Test;


@Test
public class NetSfJsonSpeedTest extends JsonSerializerSpeedTest{
	@Override
	protected JsonSerializer createDumper() {
		return new NetSfJsonSerializer();
	}
}
