package com.itsalleasy.json;

import org.testng.annotations.Test;

@Test
public class JsonDumperWithTrackingSpeedTest extends JsonSerializerSpeedTest{
	@Override
	protected JsonSerializer createDumper() {
		return new JsonDumper();
	}
}
