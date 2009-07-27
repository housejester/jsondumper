package org.merecode.json;

import org.merecode.json.JsonDumper;
import org.merecode.json.JsonSerializer;
import org.merecode.walker.PropertyFilters;
import org.merecode.walker.TrackingPolicies;
import org.testng.annotations.Test;

@Test
public class JsonDumperNoTrackingSpeedTest extends JsonSerializerSpeedTest{
	private JsonDumper dumper = new JsonDumper(PropertyFilters.IS_DEFAULT_OR_EMPTY, TrackingPolicies.TRACK_NOTHING, null, null);
	@Override
	protected JsonSerializer createDumper() {
		return dumper;
	}
}
