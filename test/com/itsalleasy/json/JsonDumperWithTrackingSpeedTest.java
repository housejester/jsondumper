package com.itsalleasy.json;

public class JsonDumperWithTrackingSpeedTest extends JsonSerializerSpeedTest{
	@Override
	protected JsonSerializer createDumper() {
		return new JsonDumper();
	}
}
