package com.itsalleasy.json;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

import junit.framework.TestCase;

public class JsonDumperTest extends TestCase{
	public void testShouldDumpStringsQuoted(){
		JsonDumper dumper = new JsonDumper();
		Object obj = "some string";
		String dump = dumper.dump(obj);
		assertEquals("\"some string\"", dump);
	}
	public void testShouldDumpIntegers(){
		JsonDumper dumper = new JsonDumper();
		Object obj = 100;
		String dump = dumper.dump(obj);
		assertEquals("100", dump);
	}
	public void testShouldTruncateFloatsWithNoDecimal(){
		JsonDumper dumper = new JsonDumper();
		Object obj = 100f;
		String dump = dumper.dump(obj);
		assertEquals("100", dump);
	}
	public void testShouldFormatFloatsToTwoDecimalsByDefault(){
		JsonDumper dumper = new JsonDumper();
		Object obj = 100.33333333f;
		String dump = dumper.dump(obj);
		assertEquals("100.33", dump);
	}
	public void testShouldRoundUpFloats(){
		JsonDumper dumper = new JsonDumper();
		Object obj = 100.338f;
		String dump = dumper.dump(obj);
		assertEquals("100.34", dump);
	}
	public void testShouldRoundUpNonDecimalPartOfFloats(){
		JsonDumper dumper = new JsonDumper();
		Object obj = 100.999f;
		String dump = dumper.dump(obj);
		assertEquals("101", dump);
	}
	public void testShouldDumpBooleanTrue(){
		JsonDumper dumper = new JsonDumper();
		Object obj = true;
		String dump = dumper.dump(obj);
		assertEquals("true", dump);
	}
	public void testShouldDumpBooleanFalse(){
		JsonDumper dumper = new JsonDumper();
		Object obj = false;
		String dump = dumper.dump(obj);
		assertEquals("false", dump);
	}
	public void testShouldDumpNull(){
		JsonDumper dumper = new JsonDumper();
		Object obj = null;
		String dump = dumper.dump(obj);
		assertEquals("null", dump);
	}
	public void testShouldDumpArrayOfStrings(){
		JsonDumper dumper = new JsonDumper();
		Object obj = new String[]{"one", "two"};
		String dump = dumper.dump(obj);
		assertEquals("[\"one\",\"two\"]", dump);
	}
	public void testShouldDumpEmptyArray(){
		JsonDumper dumper = new JsonDumper();
		Object obj = new String[]{};
		String dump = dumper.dump(obj);
		assertEquals("[]", dump);
	}
	public void testShouldDumpArrayOfIntegers(){
		JsonDumper dumper = new JsonDumper();
		Object obj = new Integer[]{1,2,3,4};
		String dump = dumper.dump(obj);
		assertEquals("[1,2,3,4]", dump);
	}
	public void testShouldDumpArrayOfInts(){
		JsonDumper dumper = new JsonDumper();
 		Object obj = new int[]{1,2,3,4};
		String dump = dumper.dump(obj);
		assertEquals("[1,2,3,4]", dump);
	}
	public void testShouldDumpArrayOfFloats(){
		JsonDumper dumper = new JsonDumper();
 		Object obj = new Float[]{1f,2.1f,3.1f,4.1f};
		String dump = dumper.dump(obj);
		assertEquals("[1,2.1,3.1,4.1]", dump);
	}
	public void testShouldDumpArrayOfPrimitiveFloats(){
		JsonDumper dumper = new JsonDumper();
 		Object obj = new float[]{1f,2.1f,3.1f,4.1f};
		String dump = dumper.dump(obj);
		assertEquals("[1,2.1,3.1,4.1]", dump);
	}
	public void testShouldDumpArrayOfBooleans(){
		JsonDumper dumper = new JsonDumper();
 		Object obj = new Boolean[]{true,true,false,true};
		String dump = dumper.dump(obj);
		assertEquals("[true,true,false,true]", dump);
	}
	public void testShouldDumpArrayOfPrimitiveBooleans(){
		JsonDumper dumper = new JsonDumper();
 		Object obj = new boolean[]{true,true,false,true};
		String dump = dumper.dump(obj);
		assertEquals("[true,true,false,true]", dump);
	}
	public void testShouldDumpArrayOfNulls(){
		JsonDumper dumper = new JsonDumper();
 		Object obj = new Object[]{null,null,null};
		String dump = dumper.dump(obj);
		assertEquals("[null,null,null]", dump);
	}
	public void testShouldDumpMixedArrayOfNulls(){
		JsonDumper dumper = new JsonDumper();
 		Object obj = new Integer[]{1,null,3};
		String dump = dumper.dump(obj);
		assertEquals("[1,null,3]", dump);
	}
	public void testShouldDumpListCollections(){
		JsonDumper dumper = new JsonDumper();
 		Object obj = Arrays.asList(new Integer[]{1,2,3});
		String dump = dumper.dump(obj);
		assertEquals("[1,2,3]", dump);
	}
	public void testShouldDumpListOfStrings(){
		JsonDumper dumper = new JsonDumper();
 		Object obj = Arrays.asList(new String[]{"1","2","3"});
		String dump = dumper.dump(obj);
		assertEquals("[\"1\",\"2\",\"3\"]", dump);
	}
	public void testShouldDumpSetCollections(){
		JsonDumper dumper = new JsonDumper();
 		Object obj = new HashSet<Object>(Arrays.asList(new Integer[]{1,2,3}));
		String dump = dumper.dump(obj);
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
		JsonDumper dumper = new JsonDumper();
 		Object obj = map;
		String dump = dumper.dump(obj);
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
		JsonDumper dumper = new JsonDumper();
 		Object obj = bean;
		String dump = dumper.dump(obj);
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
		JsonDumper dumper = new JsonDumper();
		String dump = dumper.dump(obj);
		assertEquals("{\"children\":[{\"children\":[{\"$ref\":\"\"}]}]}", dump);
	}
	public void testShouldBeAbleToDumpMultipleReferencesToSameObjectInCollections(){
		ParentBean parent = new ParentBean("bean");
		Object obj = new Object[]{parent, parent};
		JsonDumper dumper = new JsonDumper();
		String dump = dumper.dump(obj);
		assertEquals("[{\"name\":\"bean\"},{\"$ref\":\"0\"}]", dump);
	}
	public void testShouldBeAbleToDumpComplexBeanInstancesQuickly(){
		List<ParentBean> parents = new ArrayList<ParentBean>();
		for(int i=0;i<20;i++){
			ParentBean parent = new ParentBean();
			parent.setName("Parent "+i);
			List<TestBean> children = new ArrayList<TestBean>();
			for(int j=0;j<250;j++){
				TestBean test = new TestBean();
				test.setName(parent.getName()+":"+"child "+j);
				test.setAge(j);
				children.add(test);
			}
			parent.setChildren(children);
			parents.add(parent);
		}
		
		JsonDumper dumper = new JsonDumper();
 		Object obj = parents;
		long start = System.currentTimeMillis();
		String dump = dumper.dump(obj);
		long stop = System.currentTimeMillis();
		long duration = stop - start;
		assertTrue("Duration should not exceed 200 millisecond per 5000 beans (1 millisecond per 25 beans). actual: "+duration, duration < 200);
		System.out.println(duration+":"+dump.length());
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
		JsonDumper dumper = new JsonDumper();
 		Object obj = date;
		String dump = dumper.dump(obj);
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
		JsonDumper dumper = new JsonDumper();
 		Object obj = cal;
		String dump = dumper.dump(obj);
		assertEquals("new Date("+millis+")", dump);
	}
	public void testShouldEscapeDoubleQuotesInStringValues(){
		JsonDumper dumper = new JsonDumper();
		Object obj = "some \"quoted string\"";
		String dump = dumper.dump(obj);
		assertEquals("\"some \\\"quoted string\\\"\"", dump);
	}
	public void testShouldNotDumpNullBeanProperties(){
		TestBean bean = new TestBean();
		bean.setName(null);
		bean.setAge(22);
		JsonDumper dumper = new JsonDumper();
 		Object obj = bean;
		String dump = dumper.dump(obj);
		assertEquals("{\"age\":22}", dump);
	}
	public void testShouldNotDumpBooleanFalseBeanProperties(){
		TestBooleanBean bean = new TestBooleanBean("test", false);
		JsonDumper dumper = new JsonDumper();
 		Object obj = bean;
		String dump = dumper.dump(obj);
		assertEquals("{\"name\":\"test\"}", dump);
	}
	public void testShouldNotDumpEmptyStringBeanProperties(){
		TestBooleanBean bean = new TestBooleanBean("", true);
		JsonDumper dumper = new JsonDumper();
 		Object obj = bean;
		String dump = dumper.dump(obj);
		assertEquals("{\"value\":true}", dump);
	}
	public void testShouldNotDumpNumericZeroBeanProperties(){
		TestBean bean = new TestBean();
		bean.setName("foo");
		bean.setAge(0);
		JsonDumper dumper = new JsonDumper();
 		Object obj = bean;
		String dump = dumper.dump(obj);
		assertEquals("{\"name\":\"foo\"}", dump);
	}
	public void testShouldNotDumpEmptyCollectionBeanProperties(){
		ParentBean bean = new ParentBean();
		bean.setName("parent");
		bean.setChildren(new ArrayList<TestBean>());
		JsonDumper dumper = new JsonDumper();
 		Object obj = bean;
		String dump = dumper.dump(obj);
		assertEquals("{\"name\":\"parent\"}", dump);
	}
	@SuppressWarnings("unchecked")
	public void testShouldNotDumpEmptyMapBeanProperties(){
		TestBeanWithMapProperty bean = new TestBeanWithMapProperty("foo", new HashMap());
		JsonDumper dumper = new JsonDumper();
 		Object obj = bean;
		String dump = dumper.dump(obj);
		assertEquals("{\"name\":\"foo\"}", dump);
	}
	public void testShouldNotDumpNullMapProperties(){
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("age", 22);
		map.put("name", null);
		JsonDumper dumper = new JsonDumper();
 		Object obj = map;
		String dump = dumper.dump(obj);
		assertEquals("{\"age\":22}", dump);
	}
	public void testShouldNotHaveCommasInNumbers(){
		JsonDumper dumper = new JsonDumper();
 		Object obj = 100000;
		String dump = dumper.dump(obj);
		assertEquals("100000", dump);
	}
	public void testShouldDumpBooleanBeanProperties(){
		JsonDumper dumper = new JsonDumper();
 		Object obj = new TestBeanWithBooleanProperty();
		String dump = dumper.dump(obj);
		assertEquals("{\"active\":true}", dump);
	}
	public void testShouldBeAbleToDumpCharacters(){
		JsonDumper dumper = new JsonDumper();
 		Object obj = 'c';
		String dump = dumper.dump(obj);
		assertEquals("\"c\"", dump);
	}
	public void testShouldEscapeNewLineCharactersInStrings(){
		JsonDumper dumper = new JsonDumper();
 		Object obj = "line one\nline 2";
		String dump = dumper.dump(obj);
		assertEquals("\"line one\\nline 2\"", dump);
	}
	enum SimpleEnum{
		SIMPLE_VALUE
	}
	public void testShouldDumpEnumsAsStrings(){
		JsonDumper dumper = new JsonDumper();
 		Object obj = SimpleEnum.SIMPLE_VALUE;
		String dump = dumper.dump(obj);
		assertEquals("\"SIMPLE_VALUE\"", dump);
		
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
	class TestBean{
		private String name;
		private Integer age;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Integer getAge() {
			return age;
		}
		public void setAge(Integer age) {
			this.age = age;
		}
	}
	class ParentBean extends TestBean{
		private String name;
		private List<? extends TestBean> children;
		public ParentBean(){
		
		}
		public ParentBean(String name){
			setName(name);
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public List<? extends TestBean> getChildren() {
			return children;
		}
		public void setChildren(List<? extends TestBean> children) {
			this.children = children;
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
