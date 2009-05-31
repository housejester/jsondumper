package com.itsalleasy.json;

public class JsonDumperSpeedTest extends JsonSerializerSpeedTest{
	@Override
	protected JsonSerializer createDumper() {
		return new JsonDumper();
	}
}
