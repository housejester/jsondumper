package com.itsalleasy.walker;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Test
public class WalkerTest{
	private List<String> beanProperties;
	private List<Object> visited;
	private List<Object> arrayStarts;
	private List<Object> arrayEnds;
	private List<Object> revisited;
	private HashMap<Integer,Object> arrayItems;
	private Walker walker;
	private WalkerVisitor visitor; 
	@BeforeMethod()
	public void setUp(){
		beanProperties = new ArrayList<String>();
		visited = new ArrayList<Object>();
		arrayStarts = new ArrayList<Object>();
		arrayEnds = new ArrayList<Object>();
		arrayItems = new HashMap<Integer, Object>();
		
		revisited = new ArrayList<Object>();
		visitor = new WalkerVisitor(){
			public void visit(Object obj){
				visited.add(obj);
			}
			public void beforeWalkArray(Object obj){
				arrayStarts.add(obj);
			}
			public void afterWalkArray(Object obj){
				arrayEnds.add(obj);
			}
			public void beforeVisitArrayItem(Object item, int i) {
				arrayItems.put(i, item);
			}
			public void afterWalkBean(Object obj) {
			}
			public void beforeVisitBeanProperty(Object value, String name, boolean isFirst) {
				beanProperties.add(name);
			}
			public void beforeWalkBean(Object obj) {
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

		walker = new Walker(PropertyFilters.FILTER_NONE, null, null);
	}
	@Test()
	public void testShouldWalkPrimitiveIntsWithVisitToPrimitive(){
		walker.walk(1, visitor);
		assertTrue(visited.contains(1));
	}
	@Test()
	public void testShouldWalkPrimitiveStringsWithVisitToPrimitive(){
		walker.walk("foo", visitor);
		assertTrue(visited.contains("foo"));
	}
	@Test()
	public void testShouldWalkPrimitiveIntsWithBooleansToPrimitive(){
		walker.walk(true, visitor);
		assertTrue(visited.contains(true));
	}
	@Test()
	public void testShouldWalkNullsWithVisitToNull(){
		walker.walk(null, visitor);
		assertTrue(visited.contains(null));
	}
	@Test()
	public void testShouldVisitActualArrayWhenWalkingArray(){
		String[] arr = new String[]{"foo", "bar"};
		walker.walk(arr, visitor);
		assertTrue(visited.contains(arr));
	}
	@Test()
	public void testShouldWalkArraysWithVisitToEachElemInArray(){
		walker.walk(new String[]{"foo", "bar"}, visitor);
		assertTrue(visited.contains("foo"));
		assertTrue(visited.contains("bar"));
	}
	@Test()
	public void testShouldWalkListsWithVisitToEachElemInList(){
		walker.walk(Arrays.asList(new String[]{"foo", "bar"}), visitor);
		assertTrue(visited.contains("foo"));
		assertTrue(visited.contains("bar"));
	}
	@Test()
	public void testShouldWalkSetsWithVisitToEachElemInSet(){
		walker.walk(new HashSet<String>(Arrays.asList(new String[]{"foo", "bar"})), visitor);
		assertTrue(visited.contains("foo"));
		assertTrue(visited.contains("bar"));
	}
	@Test()
	public void testShouldCallArrayVisitMethodsForArrays(){
		String[] arr = new String[]{"foo", "bar"};
		walker.walk(arr, visitor);
		assertEquals(arrayStarts.size(), 1);
		assertEquals(arrayEnds.size(), 1);
	}
	@Test()
	public void testShouldCallArrayVisitMethodsForLists(){
		List list = Arrays.asList(new String[]{"foo", "bar"});
		walker.walk(list, visitor);
		assertTrue(arrayStarts.contains(list));
		assertTrue(arrayEnds.contains(list));
	}
	@Test()
	public void testShouldVisitNestedArrays(){
		walker.walk(new Object[]{new String[]{"foo", "bar"}}, visitor);
		assertTrue(visited.contains("foo"));
		assertTrue(visited.contains("bar"));
	}
	@Test()
	public void testShouldCallRevisitWhenArrayReferencedMultipleTimes(){
		String[] arr = new String[]{"foo", "bar"};
		walker.walk(new Object[]{arr, arr}, visitor);
		assertTrue(revisited.contains(arr));
	}
	@Test()
	public void testShouldCallArrayItemForEachElemInArray(){
		walker.walk(new String[]{"foo", "bar"}, visitor);
		assertTrue(arrayItems.containsValue("foo"));
		assertTrue(arrayItems.containsValue("bar"));
	}
	@Test()
	public void testShouldCallArrayItemWithCorrectIndexForEachElemInArray(){
		walker.walk(new String[]{"foo", "bar"}, visitor);
		assertSame(arrayItems.get(0), "foo");
		assertSame(arrayItems.get(1), "bar");
	}
	@Test()
	public void testShouldNotWalkAlmostBooleanBeanProperties(){
		walker.walk(new ClassWithBooleanAccessorLikeMethod(), visitor);
		assertTrue(beanProperties.isEmpty());
	}
	@Test()
	public void testShouldNotWalkAlmostBeanProperties(){
		walker.walk(new ClassWithAccessorLikeMethod(), visitor);
		assertTrue(beanProperties.isEmpty());
	}
	
	@Test()
	public void testShouldNotWalkAccessorLikeMethodsThatTakeArguments(){
		walker.walk(new ClassWithAccessorLikeMethodWithParam(), visitor);
		assertTrue(beanProperties.isEmpty());
	}
	@Test()
	public void testShouldNotWalkBooleanAccessorLikeMethodsThatTakeArguments(){
		walker.walk(new ClassWithBooleanAccessorLikeMethodWithParam(), visitor);
		assertTrue(beanProperties.isEmpty());
	}
	@Test()
	public void testShouldNotWalkAccessorLikeMethodsThatReturnVoid(){
		walker.walk(new ClassWithAccessorLikeMethodWithVoidReturn(), visitor);
		assertTrue(beanProperties.isEmpty());
	}
	@Test()
	public void testShouldNotWalkBooleanAccessorLikeMethodsThatReturnVoid(){
		walker.walk(new ClassWithBooleanAccessorLikeMethodWithVoidReturn(), visitor);
		assertTrue(beanProperties.isEmpty());
	}
	@Test()
	public void testShouldNotWalkAccessorLikeMethodsThatArePrivate(){
		walker.walk(new ClassWithPrivateAccessorLikeMethod(), visitor);
		assertTrue(beanProperties.isEmpty());
	}

	@Test()
	public void testShouldNotWalkAccessorMethodsWithClassReturnType(){
		walker.walk(new ClassWithAccessorOfTypeClass(), visitor);
		assertTrue(beanProperties.isEmpty());
	}

	@Test()
	public void testShouldWalkValidAccessorMethodsAsBeanProperties(){
		walker.walk(new ClassWithSingleStringBeanPropertyAccessor(), visitor);
		assertFalse(beanProperties.isEmpty());
	}
}

