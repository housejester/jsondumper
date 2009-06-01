package com.itsalleasy.walker;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.IdentityHashMap;
import java.util.Map;

public class RootNodeWalker implements NodeWalker{
	private PropertyFilter filter;
	public static int cache_hits = 0;
	public static int cache_misses = 0;
	private static final NodeWalkerFactory NULL_FACTORY = new NodeWalkerFactory(){
		public NodeWalker create(Object obj, NodeWalker itemWalker,	PropertyFilter filter) {
			return null;
		}
	};

	public RootNodeWalker(PropertyFilter filter){
		this.filter = filter;
	}
	public void walk(Object obj, WalkerVisitor visitor, Tracker tracker) {
		NodeWalkerFactory walkerFactory = createNodeWalkerFactory(obj); 
		if(walkerFactory == NULL_FACTORY){
			visitor.visit(obj);
			return;
		}
		if(tracker.isTracked(obj)){
			visitor.revisit(obj, tracker.getTrackedPath(obj));
			return;
		}
		tracker.track(obj);
		visitor.visit(obj);

		walkerFactory.create(obj, this, filter).walk(obj, visitor, tracker);
	}

	private NodeWalkerFactory createNodeWalkerFactory(Object obj) {
		if(obj == null || !isBeanLike(obj)){
			return NULL_FACTORY;
		}
		if(obj instanceof Iterable){
			return new NodeWalkerFactory(){
				public NodeWalker create(Object obj, NodeWalker itemWalker, PropertyFilter filter) {
					return new IterableNodeWalker((Iterable)obj, itemWalker);
				}
			};
		}else if(obj instanceof Object[]){
			return new NodeWalkerFactory(){
				public NodeWalker create(Object obj, NodeWalker itemWalker, PropertyFilter filter) {
					return new IterableNodeWalker(Arrays.asList((Object[])obj), itemWalker);
				}
			};
		}else if(obj != null && obj.getClass().isArray() ){
			return new NodeWalkerFactory(){
				public NodeWalker create(Object obj, NodeWalker itemWalker, PropertyFilter filter) {
					return new IterableNodeWalker(new PrimitiveArrayIterable(obj), itemWalker);
				}
			};
		}
		
		Iterable<NameValuePair> propertyPairs = null;
		if(obj instanceof Map){
			return new NodeWalkerFactory(){
				public NodeWalker create(Object obj, NodeWalker itemWalker, PropertyFilter filter) {
					return new ObjectNodeWalker(new MapNameValuePairs((Map)obj), itemWalker, filter);
				}
			};
		}
		return new NodeWalkerFactory(){
			public NodeWalker create(Object obj, NodeWalker itemWalker, PropertyFilter filter) {
				return new ObjectNodeWalker(new BeanNameValuePairs(obj), itemWalker, filter);
			}
		};
	}
	private boolean isBeanLike(Object obj) {
		return !(
				obj instanceof String ||
				obj instanceof Number ||
				obj instanceof Boolean || 
				obj instanceof Date || 
				obj instanceof Character ||
				obj instanceof Calendar || 
				obj instanceof Enum 
		);
	}

}
