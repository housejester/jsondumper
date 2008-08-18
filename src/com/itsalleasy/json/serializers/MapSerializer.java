/**
 * 
 */
package com.itsalleasy.json.serializers;

import java.util.Map;


public class MapSerializer extends NameValuePairsSerializer {
	@Override
	@SuppressWarnings("unchecked")
	protected Iterable<NameValuePair> getNameValuePairs(Object obj) {
		return new MapNameValuePairs(((Map<Object,Object>)obj));
	}
}
