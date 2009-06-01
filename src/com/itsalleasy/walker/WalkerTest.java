package com.itsalleasy.walker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import junit.framework.TestCase;

public class WalkerTest extends TestCase{
	private List<Object> visited;
	private List<Object> arrayStarts;
	private List<Object> arrayEnds;
	private List<Object> revisited;
	private HashMap<Integer,Object> arrayItems;
	private Walker walker;
	private WalkerVisitor visitor; 
	public void setUp(){
		visited = new ArrayList<Object>();
		arrayStarts = new ArrayList<Object>();
		arrayEnds = new ArrayList<Object>();
		arrayItems = new HashMap<Integer, Object>();
		revisited = new ArrayList<Object>();
		visitor = new WalkerVisitor(){
			public void visit(Object obj){
				visited.add(obj);
			}
			public void arrayStart(Object obj){
				arrayStarts.add(obj);
			}
			public void arrayEnd(Object obj){
				arrayEnds.add(obj);
			}
			public void arrayItem(int i, Object item) {
				arrayItems.put(i, item);
			}
			public void beanEnd(Object obj) {
			}
			public void beanProperty(String name, Object value, boolean isFirst) {
			}
			public void beanStart(Object obj) {
			}
			public void revisit(Object obj, String path) {
				revisited.add(obj);
			}
			public void endWalk(Object object) {
				// TODO Auto-generated method stub
				
			}
			public void startWalk(Object object) {
				// TODO Auto-generated method stub
				
			}
		};

		walker = new Walker();
	}
	public void testShouldWalkPrimitiveIntsWithVisitToPrimitive(){
		walker.walk(1, visitor);
		assertTrue(visited.contains(1));
	}
	public void testShouldWalkPrimitiveStringsWithVisitToPrimitive(){
		walker.walk("foo", visitor);
		assertTrue(visited.contains("foo"));
	}
	public void testShouldWalkPrimitiveIntsWithBooleansToPrimitive(){
		walker.walk(true, visitor);
		assertTrue(visited.contains(true));
	}
	public void testShouldWalkNullsWithVisitToNull(){
		walker.walk(null, visitor);
		assertTrue(visited.contains(null));
	}
	public void testShouldVisitActualArrayWhenWalkingArray(){
		String[] arr = new String[]{"foo", "bar"};
		walker.walk(arr, visitor);
		assertTrue(visited.contains(arr));
	}
	public void testShouldWalkArraysWithVisitToEachElemInArray(){
		walker.walk(new String[]{"foo", "bar"}, visitor);
		assertTrue(visited.contains("foo"));
		assertTrue(visited.contains("bar"));
	}
	public void testShouldWalkListsWithVisitToEachElemInList(){
		walker.walk(Arrays.asList(new String[]{"foo", "bar"}), visitor);
		assertTrue(visited.contains("foo"));
		assertTrue(visited.contains("bar"));
	}
	public void testShouldWalkSetsWithVisitToEachElemInSet(){
		walker.walk(new HashSet<String>(Arrays.asList(new String[]{"foo", "bar"})), visitor);
		assertTrue(visited.contains("foo"));
		assertTrue(visited.contains("bar"));
	}
	public void testShouldCallArrayVisitMethodsForArrays(){
		String[] arr = new String[]{"foo", "bar"};
		walker.walk(arr, visitor);
		assertEquals(1, arrayStarts.size());
		assertEquals(1, arrayEnds.size());
	}
	public void testShouldCallArrayVisitMethodsForLists(){
		List list = Arrays.asList(new String[]{"foo", "bar"});
		walker.walk(list, visitor);
		assertTrue(arrayStarts.contains(list));
		assertTrue(arrayEnds.contains(list));
	}
	public void testShouldVisitNestedArrays(){
		walker.walk(new Object[]{new String[]{"foo", "bar"}}, visitor);
		assertTrue(visited.contains("foo"));
		assertTrue(visited.contains("bar"));
	}
	public void testShouldCallRevisitWhenArrayReferencedMultipleTimes(){
		String[] arr = new String[]{"foo", "bar"};
		walker.walk(new Object[]{arr, arr}, visitor);
		assertTrue(revisited.contains(arr));
	}
	public void testShouldCallArrayItemForEachElemInArray(){
		walker.walk(new String[]{"foo", "bar"}, visitor);
		assertTrue(arrayItems.containsValue("foo"));
		assertTrue(arrayItems.containsValue("bar"));
	}
	public void testShouldCallArrayItemWithCorrectIndexForEachElemInArray(){
		walker.walk(new String[]{"foo", "bar"}, visitor);
		assertSame("foo", arrayItems.get(0));
		assertSame("bar", arrayItems.get(1));
	}
}
