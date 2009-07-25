package com.itsalleasy.json;

public class NetSfJsonSpeedTest extends JsonSerializerSpeedTest{
	@Override
	protected JsonSerializer createDumper() {
		return new NetSfJsonSerializer();
	}
}
