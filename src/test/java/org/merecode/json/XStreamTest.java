package org.merecode.json;

import org.testng.annotations.Test;

@Test
public class XStreamTest extends JsonSerializerTest{
	@Override
	protected JsonSerializer createDumper() {
		return new XStreamJsonSerializer();
	}

}
