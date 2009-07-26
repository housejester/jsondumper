package org.merecode.json;

import org.testng.annotations.Test;

@Test
public class JacksonTest extends JsonSerializerTest {
	@Override
	protected JsonSerializer createDumper() {
		return new JacksonSerializer();
	}

}
