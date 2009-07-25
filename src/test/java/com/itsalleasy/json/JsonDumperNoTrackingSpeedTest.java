package com.itsalleasy.json;

import org.testng.annotations.Test;

import com.itsalleasy.walker.TrackingPolicies;

@Test
public class JsonDumperNoTrackingSpeedTest extends JsonSerializerSpeedTest{
	@Override
	protected JsonSerializer createDumper() {
		return new JsonDumper(null, TrackingPolicies.TRACK_NOTHING, null, null);
	}
}
