package org.merecode.json;

import org.merecode.json.JsonDumper;
import org.merecode.json.JsonSerializer;
import org.testng.annotations.Test;


@Test
public class JsonDumperTest extends JsonSerializerTest{
	public JsonSerializer createDumper(){
		return new JsonDumper();
	}
}
