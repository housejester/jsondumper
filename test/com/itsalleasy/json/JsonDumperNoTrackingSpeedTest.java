package com.itsalleasy.json;

import com.itsalleasy.walker.PropertyFilters;
import com.itsalleasy.walker.TrackingPolicies;

public class JsonDumperNoTrackingSpeedTest extends JsonSerializerSpeedTest{
	@Override
	protected JsonSerializer createDumper() {
		return new JsonDumper(PropertyFilters.IS_DEFAULT_OR_EMPTY, TrackingPolicies.TRACK_NOTHING);
	}
}
