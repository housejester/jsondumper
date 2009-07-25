package com.itsalleasy.json;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

public abstract class JsonSerializerTest  extends TestCase{
	private JsonSerializer dumper;
	public void setUp(){
		dumper = createDumper();
	}
	protected abstract JsonSerializer createDumper();

	public void testShouldDumpStringsQuoted(){
		Object obj = "some string";
		String dump = dumper.serialize(obj);
		assertEquals("\"some string\"", dump);
	}
	public void testShouldDumpIntegers(){
		Object obj = 100;
		String dump = dumper.serialize(obj);
		assertEquals("100", dump);
	}
	public void testShouldTruncateFloatsWithNoDecimal(){
		Object obj = 100f;
		String dump = dumper.serialize(obj);
		assertEquals("100", dump);
	}
	public void testShouldFormatFloatsToTwoDecimalsByDefault(){
		Object obj = 100.33333333f;
		String dump = dumper.serialize(obj);
		assertEquals("100.33", dump);
	}
	public void testShouldRoundUpFloats(){
		Object obj = 100.338f;
		String dump = dumper.serialize(obj);
		assertEquals("100.34", dump);
	}
	public void testShouldRoundUpNonDecimalPartOfFloats(){
		Object obj = 100.999f;
		String dump = dumper.serialize(obj);
		assertEquals("101", dump);
	}
	public void testShouldDumpBooleanTrue(){
		Object obj = true;
		String dump = dumper.serialize(obj);
		assertEquals("true", dump);
	}
	public void testShouldDumpBooleanFalse(){
		Object obj = false;
		String dump = dumper.serialize(obj);
		assertEquals("false", dump);
	}
	public void testShouldDumpNull(){
		Object obj = null;
		String dump = dumper.serialize(obj);
		assertEquals("null", dump);
	}
	public void testShouldDumpArrayOfStrings(){
		Object obj = new String[]{"one", "two"};
		String dump = dumper.serialize(obj);
		assertEquals("[\"one\",\"two\"]", dump);
	}
	public void testShouldDumpEmptyArray(){
		Object obj = new String[]{};
		String dump = dumper.serialize(obj);
		assertEquals("[]", dump);
	}
	public void testShouldDumpArrayOfIntegers(){
		Object obj = new Integer[]{1,2,3,4};
		String dump = dumper.serialize(obj);
		assertEquals("[1,2,3,4]", dump);
	}
	public void testShouldDumpArrayOfInts(){
 		Object obj = new int[]{1,2,3,4};
		String dump = dumper.serialize(obj);
		assertEquals("[1,2,3,4]", dump);
	}
	public void testShouldDumpArrayOfFloats(){
 		Object obj = new Float[]{1f,2.1f,3.1f,4.1f};
		String dump = dumper.serialize(obj);
		assertEquals("[1,2.1,3.1,4.1]", dump);
	}
	public void testShouldDumpArrayOfPrimitiveFloats(){
 		Object obj = new float[]{1f,2.1f,3.1f,4.1f};
		String dump = dumper.serialize(obj);
		assertEquals("[1,2.1,3.1,4.1]", dump);
	}
	public void testShouldDumpArrayOfBooleans(){
 		Object obj = new Boolean[]{true,true,false,true};
		String dump = dumper.serialize(obj);
		assertEquals("[true,true,false,true]", dump);
	}
	public void testShouldDumpArrayOfPrimitiveBooleans(){
 		Object obj = new boolean[]{true,true,false,true};
		String dump = dumper.serialize(obj);
		assertEquals("[true,true,false,true]", dump);
	}
	public void testShouldDumpArrayOfNulls(){
 		Object obj = new Object[]{null,null,null};
		String dump = dumper.serialize(obj);
		assertEquals("[null,null,null]", dump);
	}
	public void testShouldDumpMixedArrayOfNulls(){
 		Object obj = new Integer[]{1,null,3};
		String dump = dumper.serialize(obj);
		assertEquals("[1,null,3]", dump);
	}
	public void testShouldDumpListCollections(){
 		Object obj = Arrays.asList(new Integer[]{1,2,3});
		String dump = dumper.serialize(obj);
		assertEquals("[1,2,3]", dump);
	}
	public void testShouldDumpListOfStrings(){
 		Object obj = Arrays.asList(new String[]{"1","2","3"});
		String dump = dumper.serialize(obj);
		assertEquals("[\"1\",\"2\",\"3\"]", dump);
	}
	public void testShouldDumpSetCollections(){
 		Object obj = new HashSet<Object>(Arrays.asList(new Integer[]{1,2,3}));
		String dump = dumper.serialize(obj);
		String numbersWithCommas = dump.substring(1, dump.length()-1);
		String[] numbers = numbersWithCommas.split(",");
		List<String> numbersList = Arrays.asList(numbers);
		assertTrue("no '1' in: "+dump, numbersList.contains("1"));
		assertTrue("no '2' in: "+dump, numbersList.contains("2"));
		assertTrue("no '3' in: "+dump, numbersList.contains("3"));
	}
	public void testShouldBeAbleToDumpMapsAsJavascriptPrimitiveObjects(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("key1", "value1");
		map.put("key2", 2);
		map.put("key3", true);

		Object obj = map;
		String dump = dumper.serialize(obj);
		assertEquals('{', dump.charAt(0));
		assertEquals('}', dump.charAt(dump.length()-1));
		String allKeyValues = dump.substring(1,dump.length()-1);
		String[] keyValuePairs = allKeyValues.split(",");
		List<String> keyValuePairList = Arrays.asList(keyValuePairs);
		assertTrue("should have key1: "+dump, keyValuePairList.contains("\"key1\":\"value1\""));
		assertTrue("should have key2: "+dump,keyValuePairList.contains("\"key2\":2"));
		assertTrue("should have key3: "+dump,keyValuePairList.contains("\"key3\":true"));
	}
	public void testShouldBeAbleToDumpSimpleBeanInstance(){
		TestBean bean = new TestBean();
		bean.setName("foo");
		bean.setAge(22);
 		Object obj = bean;
		String dump = dumper.serialize(obj);
		assertEquals('{', dump.charAt(0));
		assertEquals('}', dump.charAt(dump.length()-1));
		String allKeyValues = dump.substring(1,dump.length()-1);
		String[] keyValuePairs = allKeyValues.split(",");
		List<String> keyValuePairList = Arrays.asList(keyValuePairs);
		assertTrue("should have name: "+dump, keyValuePairList.contains("\"name\":\"foo\""));
		assertTrue("should have age: "+dump,keyValuePairList.contains("\"age\":22"));
	}
	
	public void testShouldBeAbleToDumpCircularBeanReferenceGraphs(){
		ParentBean parent = new ParentBean();
		ParentBean child = new ParentBean();
		parent.setChildren(Collections.singletonList(child));
		child.setChildren(Collections.singletonList(parent));
		Object obj = parent;
		String dump = dumper.serialize(obj);
		assertEquals("{\"children\":[{\"children\":[{\"$ref\":\"\"}]}]}", dump);
	}
	public void testShouldBeAbleToDumpMultipleReferencesToSameObjectInCollections(){
		ParentBean parent = new ParentBean("bean");
		Object obj = new Object[]{parent, parent};
		String dump = dumper.serialize(obj);
		assertEquals("[{\"name\":\"bean\"},{\"$ref\":\"0\"}]", dump);
	}
	public void testShouldBeAbleToDumpDates(){
		Calendar cal = Calendar.getInstance();
		int year = 2000;
		int month = 8;
		int day = 6;
		int hour = 10;
		int minute = 22;
		int second = 34;
		cal.set(year, month, day, hour, minute, second);
		Date date = cal.getTime();
		long millis = date.getTime();
 		Object obj = date;
		String dump = dumper.serialize(obj);
		assertEquals("new Date("+millis+")", dump);
	}
	public void testShouldBeAbleToDumpCalendars(){
		Calendar cal = Calendar.getInstance();
		int year = 2000;
		int month = 8;
		int day = 6;
		int hour = 10;
		int minute = 22;
		int second = 34;
		cal.set(year, month, day, hour, minute, second);
		Date date = cal.getTime();
		long millis = date.getTime();
 		Object obj = cal;
		String dump = dumper.serialize(obj);
		assertEquals("new Date("+millis+")", dump);
	}
	public void testShouldEscapeDoubleQuotesInStringValues(){
		Object obj = "some \"quoted string\"";
		String dump = dumper.serialize(obj);
		assertEquals("\"some \\\"quoted string\\\"\"", dump);
	}
	public void testShouldEscapeForwardSlashesInStrings(){
		Object obj = "some string/with/slashes";
		String dump = dumper.serialize(obj);
		assertEquals("\"some string\\/with\\/slashes\"", dump);
	}
	public void testShouldProduceUnicodeForNonUtf8Charset(){
		Object obj = "\u6C34";
		String dump = dumper.serialize(obj);
		assertEquals("\"\\u6c34\"", dump);
	}
	public void testShouldNotDumpNullBeanProperties(){
		TestBean bean = new TestBean();
		bean.setName(null);
		bean.setAge(22);
 		Object obj = bean;
		String dump = dumper.serialize(obj);
		assertEquals("{\"age\":22}", dump);
	}
	public void testShouldNotDumpBooleanFalseBeanProperties(){
		TestBooleanBean bean = new TestBooleanBean("test", false);
 		Object obj = bean;
		String dump = dumper.serialize(obj);
		assertEquals("{\"name\":\"test\"}", dump);
	}
	public void testShouldNotDumpEmptyStringBeanProperties(){
		TestBooleanBean bean = new TestBooleanBean("", true);
 		Object obj = bean;
		String dump = dumper.serialize(obj);
		assertEquals("{\"value\":true}", dump);
	}
	public void testShouldNotDumpNumericZeroBeanProperties(){
		TestBean bean = new TestBean();
		bean.setName("foo");
		bean.setAge(0);
 		Object obj = bean;
		String dump = dumper.serialize(obj);
		assertEquals("{\"name\":\"foo\"}", dump);
	}
	public void testShouldNotDumpEmptyCollectionBeanProperties(){
		ParentBean bean = new ParentBean();
		bean.setName("parent");
		bean.setChildren(new ArrayList<TestBean>());

 		Object obj = bean;
		String dump = dumper.serialize(obj);
		assertEquals("{\"name\":\"parent\"}", dump);
	}
	@SuppressWarnings("unchecked")
	public void testShouldNotDumpEmptyMapBeanProperties(){
		TestBeanWithMapProperty bean = new TestBeanWithMapProperty("foo", new HashMap());

 		Object obj = bean;
		String dump = dumper.serialize(obj);
		assertEquals("{\"name\":\"foo\"}", dump);
	}
	public void testShouldNotDumpNullMapProperties(){
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("age", 22);
		map.put("name", null);

 		Object obj = map;
		String dump = dumper.serialize(obj);
		assertEquals("{\"age\":22}", dump);
	}
	public void testShouldNotHaveCommasInNumbers(){
 		Object obj = 100000;
		String dump = dumper.serialize(obj);
		assertEquals("100000", dump);
	}
	public void testShouldDumpBooleanBeanProperties(){
 		Object obj = new TestBeanWithBooleanProperty();
		String dump = dumper.serialize(obj);
		assertEquals("{\"active\":true}", dump);
	}
	public void testShouldBeAbleToDumpCharacters(){
 		Object obj = 'c';
		String dump = dumper.serialize(obj);
		assertEquals("\"c\"", dump);
	}
	public void testShouldEscapeNewLineCharactersInStrings(){
 		Object obj = "line one\nline 2";
		String dump = dumper.serialize(obj);
		assertEquals("\"line one\\nline 2\"", dump);
	}
	enum SimpleEnum{
		SIMPLE_VALUE
	}
	public void testShouldDumpEnumsAsStrings(){
 		Object obj = SimpleEnum.SIMPLE_VALUE;
		String dump = dumper.serialize(obj);
		assertEquals("\"SIMPLE_VALUE\"", dump);
	}
	public void testShouldNotOutputRefsWhenObjectsAreEqualButNotSameRef(){
 		Object obj = Arrays.asList(new TestBean[]{new TestBean("Foo"), new TestBean("Foo")});
		String dump = dumper.serialize(obj);
		assertEquals("[{\"name\":\"Foo\"},{\"name\":\"Foo\"}]", dump);
	}
	/*
	 * public void testShouldNotAllowDangerousJavascriptInStrings()
	 */
	class TestBeanWithBooleanProperty{
		public boolean isActive(){
			return true;
		}
	}
	class TestBooleanBean{
		private String name;
		private boolean value;
		public TestBooleanBean(String name, boolean value){
			this.name = name;
			this.value = value;
		}
		public String getName() {
			return name;
		}
		public boolean isValue() {
			return value;
		}
	}
	class TestBeanWithMapProperty{
		private String name;
		@SuppressWarnings("unchecked")
		private Map map;
		@SuppressWarnings("unchecked")
		public TestBeanWithMapProperty(String name, Map map){
			this.name = name;
			this.map = map;
		}
		public String getName() {
			return name;
		}
		@SuppressWarnings("unchecked")
		public Map getMap() {
			return map;
		}
		
	}
}