package com.itsalleasy.json;

import org.testng.annotations.Test;

@Test
public class NetSfJsonTest extends JsonSerializerTest{
	@Override
	protected JsonSerializer createDumper() {
		return new NetSfJsonSerializer();
	}

}
