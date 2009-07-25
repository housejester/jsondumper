package com.itsalleasy.json;

import org.testng.annotations.Test;

@Test
public class JsonDumperTest extends JsonSerializerTest{
	public JsonSerializer createDumper(){
		return new JsonDumper();
	}
}
