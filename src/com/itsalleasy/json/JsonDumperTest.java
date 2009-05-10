package com.itsalleasy.json;

public class JsonDumperTest extends JsonSerializerTest{
	public JsonSerializer createDumper(){
		return new JsonDumper();
	}
}
