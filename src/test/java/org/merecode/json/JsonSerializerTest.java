package org.merecode.json;

import org.merecode.json.JsonSerializer;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;


import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Test
public abstract class JsonSerializerTest{
	private JsonSerializer dumper;
	@BeforeMethod()
	public void setUp(){
		dumper = createDumper();
	}
	protected abstract JsonSerializer createDumper();

	@Test()
	public void testShouldDumpStringsQuoted(){
		Object obj = "some string";
		String dump = dumper.serialize(obj);
		assertEquals(dump, "\"some string\"");
	}
	
	@Test()
	public void testShouldDumpIntegers(){
		Object obj = 100;
		String dump = dumper.serialize(obj);
		assertEquals(dump, "100");
	}
	
	@Test()
	public void testShouldDumpFloats(){
		Object obj = 100f;
		String dump = dumper.serialize(obj);
		assertEquals(dump, "100.0");
	}

	@Test()
	public void testShouldRenderBigDecimalsAsDecimals(){
		Object obj = new BigDecimal("100.33");
		String dump = dumper.serialize(obj);
		assertEquals(dump, "100.33");
	}

	@Test()
	public void testShouldRenderBigDecimalWithManyDecimalDigits(){
		String decimalText = "100.0123456789012345678901234567890123456789";
		Object obj = new BigDecimal(decimalText);
		String dump = dumper.serialize(obj);
		assertEquals(dump, decimalText);
	}

	@Test()
	public void testShouldNotLimitDecimalPrecisionForFloats(){
		float num = 100.336666666666f;
		String dump = dumper.serialize(num);
		assertEquals(dump, String.valueOf(num));
	}

	@Test()
	public void testShouldLimitDecimalPrecisionForFloatsWhenConfiguredTo(){
		dumper.setMaxDecimalDigits(2);
		float num = 100.336666666666f;
		String dump = dumper.serialize(num);
		assertEquals(dump, "100.34");
	}

	@Test()
	public void testShouldNotRenderZeroDecimalValueWhenMinDecimalsIsZero(){
		dumper.setMinDecimalDigits(0);
		Object obj = 100f;
		String dump = dumper.serialize(obj);
		assertEquals(dump, "100");
	}

	@Test()
	public void testSettingMinShouldNotAffectDefaultMaxDecimalDigits(){
		Object obj = 33.012345678901234567890f;
		String beforeValue = dumper.serialize(obj);
		dumper.setMinDecimalDigits(2);
		assertEquals(dumper.serialize(obj), beforeValue);
	}

	@Test()
	public void testSettingMaxShouldNotAffectDefaultMinDecimalDigits(){
		Object obj = 33f;
		String beforeValue = dumper.serialize(obj);
		dumper.setMaxDecimalDigits(10);
		assertEquals(dumper.serialize(obj), beforeValue);
	}

	@Test()
	public void testShouldRenderCorrectDecimalsWhenMinDecimalsSet(){
		dumper.setMinDecimalDigits(4);
		Object obj = 100f;
		String dump = dumper.serialize(obj);
		assertEquals(dump, "100.0000");
	}


	@Test()
	public void testShouldNotLimitDecimalPrecisionForDoubles(){
		double num = 100.336666666666777888999d;
		String dump = dumper.serialize(num);
		assertEquals(dump, String.valueOf(num));
	}

	@Test()
	public void testShouldRoundUpNonDecimalPartOfFloats(){
		Object obj = 100.9999999999f;
		String dump = dumper.serialize(obj);
		assertEquals(dump, "101.0");
	}

	@Test()
	public void testShouldDumpBooleanTrue(){
		Object obj = true;
		String dump = dumper.serialize(obj);
		assertEquals(dump, "true");
	}
	@Test()
	public void testShouldDumpBooleanFalse(){
		Object obj = false;
		String dump = dumper.serialize(obj);
		assertEquals(dump, "false");
	}
	@Test()
	public void testShouldDumpNull(){
		Object obj = null;
		String dump = dumper.serialize(obj);
		assertEquals(dump, "null");
	}
	@Test()
	public void testShouldDumpArrayOfStrings(){
		Object obj = new String[]{"one", "two"};
		String dump = dumper.serialize(obj);
		assertEquals(dump, "[\"one\",\"two\"]");
	}
	@Test()
	public void testShouldDumpEmptyArray(){
		Object obj = new String[]{};
		String dump = dumper.serialize(obj);
		assertEquals(dump, "[]");
	}
	@Test()
	public void testShouldDumpArrayOfIntegers(){
		Object obj = new Integer[]{1,2,3,4};
		String dump = dumper.serialize(obj);
		assertEquals(dump, "[1,2,3,4]");
	}
	@Test()
	public void testShouldDumpArrayOfInts(){
 		Object obj = new int[]{1,2,3,4};
		String dump = dumper.serialize(obj);
		assertEquals(dump, "[1,2,3,4]");
	}
	@Test()
	public void testShouldDumpArrayOfFloats(){
 		Object obj = new Float[]{1f,2.1f,3.1f,4.1f};
		String dump = dumper.serialize(obj);
		assertEquals(dump, "[1.0,2.1,3.1,4.1]");
	}
	@Test()
	public void testShouldDumpArrayOfPrimitiveFloats(){
 		Object obj = new float[]{1f,2.1f,3.1f,4.1f};
		String dump = dumper.serialize(obj);
		assertEquals(dump, "[1.0,2.1,3.1,4.1]");
	}
	@Test()
	public void testShouldDumpArrayOfBooleans(){
 		Object obj = new Boolean[]{true,true,false,true};
		String dump = dumper.serialize(obj);
		assertEquals(dump, "[true,true,false,true]");
	}
	@Test()
	public void testShouldDumpArrayOfPrimitiveBooleans(){
 		Object obj = new boolean[]{true,true,false,true};
		String dump = dumper.serialize(obj);
		assertEquals(dump, "[true,true,false,true]");
	}
	@Test()
	public void testShouldDumpArrayOfNulls(){
 		Object obj = new Object[]{null,null,null};
		String dump = dumper.serialize(obj);
		assertEquals(dump, "[null,null,null]");
	}
	@Test()
	public void testShouldDumpMixedArrayOfNulls(){
 		Object obj = new Integer[]{1,null,3};
		String dump = dumper.serialize(obj);
		assertEquals(dump, "[1,null,3]");
	}
	@Test()
	public void testShouldDumpListCollections(){
 		Object obj = Arrays.asList(new Integer[]{1,2,3});
		String dump = dumper.serialize(obj);
		assertEquals(dump, "[1,2,3]");
	}
	@Test()
	public void testShouldDumpListOfStrings(){
 		Object obj = Arrays.asList(new String[]{"1","2","3"});
		String dump = dumper.serialize(obj);
		assertEquals(dump, "[\"1\",\"2\",\"3\"]");
	}
	@Test()
	public void testShouldDumpSetCollections(){
 		Object obj = new HashSet<Object>(Arrays.asList(new Integer[]{1,2,3}));
		String dump = dumper.serialize(obj);
		String numbersWithCommas = dump.substring(1, dump.length()-1);
		String[] numbers = numbersWithCommas.split(",");
		List<String> numbersList = Arrays.asList(numbers);
		assertTrue(numbersList.contains("1"), "no '1' in: "+dump);
		assertTrue(numbersList.contains("2"), "no '2' in: "+dump);
		assertTrue(numbersList.contains("3"), "no '3' in: "+dump);
	}
	@Test()
	public void testShouldBeAbleToDumpMapsAsJavascriptPrimitiveObjects(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("key1", "value1");
		map.put("key2", 2);
		map.put("key3", true);

		Object obj = map;
		String dump = dumper.serialize(obj);
		assertEquals(dump.charAt(0), '{');
		assertEquals(dump.charAt(dump.length()-1), '}');
		String allKeyValues = dump.substring(1,dump.length()-1);
		String[] keyValuePairs = allKeyValues.split(",");
		List<String> keyValuePairList = Arrays.asList(keyValuePairs);
		assertTrue(keyValuePairList.contains("\"key1\":\"value1\""), "should have key1: "+dump);
		assertTrue(keyValuePairList.contains("\"key2\":2"), "should have key2: "+dump);
		assertTrue(keyValuePairList.contains("\"key3\":true"), "should have key3: "+dump);
	}
	@Test()
	public void testShouldBeAbleToDumpSimpleBeanInstance(){
		TestBean bean = new TestBean();
		bean.setName("foo");
		bean.setAge(22);
 		Object obj = bean;
		String dump = dumper.serialize(obj).replaceAll(" ", "");
		assertEquals(dump.charAt(0), '{');
		assertEquals(dump.charAt(dump.length()-1), '}');
		String allKeyValues = dump.substring(1,dump.length()-1);
		String[] keyValuePairs = allKeyValues.split(",");
		List<String> keyValuePairList = Arrays.asList(keyValuePairs);
		assertTrue(keyValuePairList.contains("\"name\":\"foo\""), "should have name: "+dump);
		assertTrue(keyValuePairList.contains("\"age\":22"), "should have age: "+dump);
	}
	
	@Test()
	public void testShouldBeAbleToDumpCircularBeanReferenceGraphs(){
		ParentBean parent = new ParentBean();
		ParentBean child = new ParentBean();
		parent.setChildren(Collections.singletonList(child));
		child.setChildren(Collections.singletonList(parent));
		Object obj = parent;
		String dump = dumper.serialize(obj);
		assertEquals(dump, "{\"children\":[{\"children\":[{\"$ref\":\"\"}]}]}");
	}
	@Test()
	public void testShouldBeAbleToDumpMultipleReferencesToSameObjectInCollections(){
		ParentBean parent = new ParentBean("bean");
		Object obj = new Object[]{parent, parent};
		String dump = dumper.serialize(obj);
		assertEquals(dump, "[{\"name\":\"bean\"},{\"$ref\":\"0\"}]");
	}
	@Test()
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
		assertEquals(dump, ""+millis);
	}
	@Test()
	public void testShouldBeAbleToDumpCalendarsAsTimestamp(){
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
		assertEquals(dump, ""+millis);
	}
	@Test()
	public void testShouldEscapeDoubleQuotesInStringValues(){
		Object obj = "some \"quoted string\"";
		String dump = dumper.serialize(obj);
		assertEquals(dump, "\"some \\\"quoted string\\\"\"");
	}
	@Test()
	public void testShouldNotEscapeForwardSlashesInStrings(){
		Object obj = "some string/with/slashes";
		String dump = dumper.serialize(obj);
		assertEquals(dump, "\"some string/with/slashes\"");
	}
	@Test()
	public void testShouldEscapeForwardSlashesInStringsIfConfiguredTo(){
		dumper.setEscapeForwardSlashes(true);
		Object obj = "some string/with/slashes";
		String dump = dumper.serialize(obj);
		assertEquals(dump, "\"some string\\/with\\/slashes\"");
	}
	@Test()
	public void testShouldUnicodeEscapeNonAsciiRangeIfConfiguredTo(){
		dumper.setEscapeNonAsciiRange(true);
		Object obj = "\u6C34";
		String dump = dumper.serialize(obj);
		assertEquals(dump, "\"\\u6c34\"");
	}
	@Test()
	public void testShouldSupportNonAsciiChars(){
		Object obj = "\u6C34";
		String dump = dumper.serialize(obj);
		assertEquals(dump, "\"\u6c34\"");
	}
	@Test()
	public void testShouldNotDumpNullBeanProperties(){
		TestBean bean = new TestBean();
		bean.setName(null);
		bean.setAge(22);
 		Object obj = bean;
		String dump = dumper.serialize(obj).replaceAll(" ", "");
		assertEquals(dump, "{\"age\":22}");
	}
	@Test()
	public void testShouldDumpNullBeanPropertiesWhenConfiguredTo(){
		dumper.setSkipNulls(false);
		TestBean bean = new TestBean();
		bean.setName(null);
		bean.setAge(22);
 		Object obj = bean;
		String dump = dumper.serialize(obj).replaceAll(" ", "");
		System.out.println(dump);
		assertTrue(dump.contains("null"));
	}
	@Test()
	public void testShouldNotDumpBooleanFalseBeanProperties(){
		TestBooleanBean bean = new TestBooleanBean("test", false);
 		Object obj = bean;
		String dump = dumper.serialize(obj);
		assertEquals(dump, "{\"name\":\"test\"}");
	}
	@Test()
	public void testShouldNotDumpEmptyStringBeanProperties(){
		TestBooleanBean bean = new TestBooleanBean("", true);
 		Object obj = bean;
		String dump = dumper.serialize(obj);
		assertEquals(dump, "{\"value\":true}");
	}
	@Test()
	public void testShouldNotDumpNumericZeroBeanProperties(){
		TestBean bean = new TestBean();
		bean.setName("foo");
		bean.setAge(0);
 		Object obj = bean;
		String dump = dumper.serialize(obj);
		assertEquals(dump, "{\"name\":\"foo\"}");
	}
	@Test()
	public void testShouldNotDumpEmptyCollectionBeanProperties(){
		dumper.setSkipEmptyCollections(true);
		ParentBean bean = new ParentBean();
		bean.setName("parent");
		bean.setChildren(new ArrayList<TestBean>());

 		Object obj = bean;
		String dump = dumper.serialize(obj);
		assertEquals(dump, "{\"name\":\"parent\"}");
	}
	@Test()
	public void testShouldDumpEmptyCollectionBeanPropertiesWhenConfigured(){
		dumper.setSkipEmptyCollections(false);
		ParentBean bean = new ParentBean();
		bean.setName("parent");
		bean.setChildren(new ArrayList<TestBean>());

 		Object obj = bean;
		String dump = dumper.serialize(obj);
		assertTrue(dump.contains("[]"));
	}
	@Test()
	@SuppressWarnings("unchecked")
	public void testShouldNotDumpEmptyMapBeanProperties(){
		TestBeanWithMapProperty bean = new TestBeanWithMapProperty("foo", new HashMap());

 		Object obj = bean;
		String dump = dumper.serialize(obj);
		assertEquals(dump, "{\"name\":\"foo\"}");
	}
	@Test()
	public void testShouldNotDumpNullMapProperties(){
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("age", 22);
		map.put("name", null);

 		Object obj = map;
		String dump = dumper.serialize(obj);
		assertEquals(dump, "{\"age\":22}");
	}
	@Test()
	public void testShouldNotHaveCommasInNumbers(){
 		Object obj = 100000;
		String dump = dumper.serialize(obj);
		assertEquals(dump, "100000");
	}
	@Test()
	public void testShouldDumpBooleanBeanProperties(){
 		Object obj = new TestBeanWithBooleanProperty();
		String dump = dumper.serialize(obj);
		assertEquals(dump, "{\"active\":true}");
	}
	@Test()
	public void testShouldBeAbleToDumpCharacters(){
 		Object obj = 'c';
		String dump = dumper.serialize(obj);
		assertEquals(dump, "\"c\"");
	}
	@Test()
	public void testShouldEscapeNewLineCharactersInStrings(){
 		Object obj = "line one\nline 2";
		String dump = dumper.serialize(obj);
		assertEquals(dump, "\"line one\\nline 2\"");
	}
	enum SimpleEnum{
		SIMPLE_VALUE
	}
	@Test()
	public void testShouldDumpEnumsAsStrings(){
 		Object obj = SimpleEnum.SIMPLE_VALUE;
		String dump = dumper.serialize(obj);
		assertEquals(dump, "\"SIMPLE_VALUE\"");
	}
	@Test()
	public void testShouldNotOutputRefsWhenObjectsAreEqualButNotSameRef(){
 		Object obj = Arrays.asList(new TestBean[]{new TestBean("Foo"), new TestBean("Foo")});
		String dump = dumper.serialize(obj);
		assertEquals( dump, "[{\"name\":\"Foo\"},{\"name\":\"Foo\"}]");
	}
}
