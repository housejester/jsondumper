/**
 * 
 */
package com.itsalleasy.json.serializers;

public class BeanSerializer extends NameValuePairsSerializer {

	@Override
	protected Iterable<NameValuePair> getNameValuePairs(Object obj) {
		return new BeanNameValuePairs(obj);
	}
}
