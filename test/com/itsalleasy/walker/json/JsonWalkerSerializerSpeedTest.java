package com.itsalleasy.walker.json;

import com.itsalleasy.json.JsonSerializer;
import com.itsalleasy.json.JsonSerializerSpeedTest;

public class JsonWalkerSerializerSpeedTest extends JsonSerializerSpeedTest{
	@Override
	protected JsonSerializer createDumper() {
		return new JsonWalkerSerializer();
	}
}
