package org.merecode.json;

import org.merecode.json.JsonDumper;
import org.merecode.json.JsonSerializer;
import org.testng.annotations.Test;


@Test
public class JsonDumperWithTrackingSpeedTest extends JsonSerializerSpeedTest{
	@Override
	protected JsonSerializer createDumper() {
		return new JsonDumper();
	}
}
