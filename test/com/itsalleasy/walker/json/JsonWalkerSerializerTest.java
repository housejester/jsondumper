package com.itsalleasy.walker.json;

import com.itsalleasy.json.JsonSerializer;
import com.itsalleasy.json.JsonSerializerTest;

public class JsonWalkerSerializerTest extends JsonSerializerTest{
	public JsonSerializer createDumper(){
		return new JsonWalkerSerializer();
	}
}
