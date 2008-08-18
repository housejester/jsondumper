/**
 * 
 */
package com.itsalleasy.json.serializers;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;

import com.itsalleasy.json.JsonWriter;
import com.itsalleasy.json.JsonSerializer;

public class NumberSerializer implements JsonSerializer {
	public void toJson(Object obj, JsonWriter context) throws IOException {
		Number num = (Number)obj;
		if(num instanceof Float || num instanceof Double || num instanceof BigDecimal){
			NumberFormat format = NumberFormat.getInstance();
			format.setMaximumFractionDigits(2);
			format.setGroupingUsed(false);
			context.append(format.format(num));
		}else{
			context.append(num.toString());
		}
	}
}