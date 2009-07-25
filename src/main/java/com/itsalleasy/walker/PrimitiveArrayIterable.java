package com.itsalleasy.walker;

import java.util.Iterator;

public class PrimitiveArrayIterable implements Iterable<Object>{
	private Object arr;
	public PrimitiveArrayIterable(Object arr){
		this.arr = arr;
	}
	public Iterator<Object> iterator() {
		return new PrimitiveArrayIterator(arr);
	}

}
