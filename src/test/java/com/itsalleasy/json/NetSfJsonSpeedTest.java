package com.itsalleasy.json;

import org.testng.annotations.Test;

@Test
public class NetSfJsonSpeedTest extends JsonSerializerSpeedTest{
	@Override
	protected JsonSerializer createDumper() {
		return new NetSfJsonSerializer();
	}
}
