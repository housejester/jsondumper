package com.itsalleasy.json;

public class NetSfJsonTest extends JsonSerializerTest{
	@Override
	protected JsonSerializer createDumper() {
		return new NetSfJsonSerializer();
	}

}
