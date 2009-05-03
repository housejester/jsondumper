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
    private static final ThreadLocal <NumberFormat> formatter = 
        new ThreadLocal <NumberFormat> () {
            @Override protected NumberFormat initialValue() {
    			NumberFormat format = NumberFormat.getInstance();
    			format.setMaximumFractionDigits(2);
    			format.setGroupingUsed(false);
                return format;
            }
    	};
    public void toJson(Object obj, JsonWriter context) throws IOException {
		Number num = (Number)obj;
		if(num instanceof Float || num instanceof Double || num instanceof BigDecimal){
			context.append(formatter.get().format(num));
		}else{
			context.append(num.toString());
		}
	}
}