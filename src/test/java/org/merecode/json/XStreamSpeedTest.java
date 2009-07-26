package org.merecode.json;

import org.testng.annotations.Test;

@Test
public class XStreamSpeedTest extends JsonSerializerSpeedTest{
	@Override
	protected JsonSerializer createDumper() {
		return new XStreamJsonSerializer();
	}

}
