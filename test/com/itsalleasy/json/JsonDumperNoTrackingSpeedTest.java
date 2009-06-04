package com.itsalleasy.json;

import com.itsalleasy.walker.TrackingPolicies;

public class JsonDumperNoTrackingSpeedTest extends JsonSerializerSpeedTest{
	@Override
	protected JsonSerializer createDumper() {
		return new JsonDumper(null, TrackingPolicies.TRACK_NOTHING, null, null);
	}
}
