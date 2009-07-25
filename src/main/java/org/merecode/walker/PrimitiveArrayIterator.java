package org.merecode.walker;

import java.lang.reflect.Array;
import java.util.Iterator;

public class PrimitiveArrayIterator implements Iterator<Object>{
	private Object arr;
	private int size;
	private int nextIndex;
	public PrimitiveArrayIterator(Object arr){
		this.arr = arr;
		nextIndex = 0;
		size = Array.getLength(arr);
	}
	public boolean hasNext() {
		return nextIndex < size;
	}

	public Object next() {
		return Array.get(arr, nextIndex++);
	}

	public void remove() {
	}
}
