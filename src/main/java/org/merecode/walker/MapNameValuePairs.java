package org.merecode.walker;

import java.util.Iterator;
import java.util.Map;


public class MapNameValuePairs implements NameValuePairs{
	private Map<Object, Object> map;
	public MapNameValuePairs(Map<Object, Object> map){
		this.map = map;
	}
	public Iterator<NameValuePair> iterator() {
		return new MapPropertiesIterator(map);
	}
}
class MapPropertiesIterator implements Iterator<NameValuePair>{
	private Iterator<Map.Entry<Object, Object>> entryIterator;
	public MapPropertiesIterator(Map<Object, Object> map){
		entryIterator = map.entrySet().iterator();
	}
	public boolean hasNext() {
		return entryIterator.hasNext();
	}

	public NameValuePair next() {
		Map.Entry<?, ?> entry = entryIterator.next();
		return new NameValuePair(entry.getKey().toString(), entry.getValue());
	}

	public void remove() {
	}
	
}
