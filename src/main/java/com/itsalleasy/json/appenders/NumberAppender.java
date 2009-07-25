/**
 * 
 */
package com.itsalleasy.json.appenders;

import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.text.NumberFormat;

import com.itsalleasy.json.Appender;

public class NumberAppender implements Appender {
    private static final ThreadLocal <NumberFormat> formatter = 
        new ThreadLocal <NumberFormat> () {
            @Override protected NumberFormat initialValue() {
    			NumberFormat format = NumberFormat.getInstance();
    			format.setMaximumFractionDigits(2);
    			format.setGroupingUsed(false);
                return format;
            }
    	};
	public void append(Object obj, Writer writer) throws IOException {
		Number num = (Number)obj;
		if(num instanceof Float || num instanceof Double || num instanceof BigDecimal){
			writer.append(formatter.get().format(num));
		}else{
			writer.append(num.toString());
		}
	}
}