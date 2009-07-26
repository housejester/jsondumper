package org.merecode.json;

import org.testng.annotations.Test;

@Test
public class JacksonSpeedTest extends JsonSerializerSpeedTest{
	@Override
	protected JsonSerializer createDumper() {
		return new JacksonSerializer();
	}
}
