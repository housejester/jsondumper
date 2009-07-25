package org.merecode.json;

import org.merecode.json.JsonSerializer;
import org.testng.annotations.Test;


import static org.testng.Assert.assertEquals;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Test
public abstract class JsonSerializerSpeedTest{
	public static final int NUM_RUNS = 100;
	protected abstract JsonSerializer createDumper();

	@Test()
	public void testShouldBeAbleToDumpComplexBeanInstancesCorrectly(){
		JsonSerializer dumper = createDumper();
		List<ParentBean> parents = createComplexBean();
		String json = dumper.serialize(parents);
		JSONArray arr = JSONArray.fromObject(json);
		assertEquals(arr.size(), parents.size());
		for(int i=0;i<arr.size();i++){
			ParentBean beanParent = parents.get(i);
			JSONObject jsonParent = arr.getJSONObject(i);
			
			assertEquals(jsonParent.getString("name"), beanParent.getName());

			List<? extends TestBean> beanChildren = beanParent.getChildren();
			JSONArray jsonChildren = jsonParent.getJSONArray("children");
			assertEquals(jsonChildren.size(), beanChildren.size());
			
			for(int j=0; j<jsonChildren.size();j++){
				TestBean beanChild = beanChildren.get(j);
				JSONObject jsonChild = jsonChildren.getJSONObject(j);
				assertEquals(new Integer(jsonChild.getInt("age")), beanChild.getAge());
				assertEquals(jsonChild.getString("name"), beanChild.getName());
			}
		}
	}
	@Test()
	public void testShouldBeAbleToDumpComplexBeanInstancesQuickly(){
		List<ParentBean> parents = createComplexBean();

		List<Long> times = new ArrayList<Long>();
		List<Long> mems = new ArrayList<Long>();
		free();
		
		for(int i=0; i<NUM_RUNS; i++){
			doRun(i, parents, times, mems);
		}
		
		System.out.println(this.getClass().getSimpleName()+" average (time/mem):" + avg(times) + "/" + avg(mems) + "KB" );
	}

	private List<ParentBean> createComplexBean() {
		List<ParentBean> parents = new ArrayList<ParentBean>();
		for(int i=0;i<20;i++){
			ParentBean parent = new ParentBean();
			parent.setName("Parent "+i);
			List<TestBean> children = new ArrayList<TestBean>();
			for(int j=0;j<250;j++){
				TestBean test = new TestBean();
				test.setName(parent.getName()+":"+"child "+j);
				test.setAge(j+1);
				children.add(test);
			}
			parent.setChildren(children);
			parents.add(parent);
		}
		return parents;
	}
	private long avg(List<Long> times){
		long total = 0;
		for(int i=1; i<times.size(); i++){
			total += times.get(i);
		}
		return total / (times.size()-1);
	}
	private void doRun(int runNumber, Object obj, List<Long> times, List<Long> mems){
		MemoryMXBean memory = ManagementFactory.getMemoryMXBean();
		free();
		long memstart = memory.getHeapMemoryUsage().getUsed();
		JsonSerializer dumper = createDumper();
		long start = System.currentTimeMillis();
		dumper.serialize(obj);
		long stop = System.currentTimeMillis();
		long duration = stop - start;
		times.add(duration);
		free();
		long memafter = memory.getHeapMemoryUsage().getUsed();
		long memdiff = memafter - memstart;
		if(memdiff > 0){
			mems.add(memdiff/1024);
		}
		System.out.println("duration/mem : "+duration+"/"+(memdiff/1024f)+"KB");
	}
	private void free(){
		System.gc(); System.gc();
	}
}
