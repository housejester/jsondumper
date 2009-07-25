package org.merecode.json;

import org.merecode.json.JsonDumper;
import org.merecode.json.JsonSerializer;
import org.merecode.walker.TrackingPolicies;
import org.testng.annotations.Test;


@Test
public class JsonDumperNoTrackingSpeedTest extends JsonSerializerSpeedTest{
	@Override
	protected JsonSerializer createDumper() {
		return new JsonDumper(null, TrackingPolicies.TRACK_NOTHING, null, null);
	}
}
