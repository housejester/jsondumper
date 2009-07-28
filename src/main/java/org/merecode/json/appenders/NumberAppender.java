/**
 * 
 */
package org.merecode.json.appenders;

import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.merecode.json.Appender;


public class NumberAppender implements Appender {
    private DecimalFormat formatter;
    
    public NumberAppender(){
    	formatter = new DecimalFormat();
		formatter.setMinimumFractionDigits(0);
		formatter.setMaximumFractionDigits(2);
		formatter.setGroupingUsed(false);
   	};

   	public void append(Object obj, Writer writer) throws IOException {
		Number num = (Number)obj;
		if(num instanceof Float || num instanceof Double || num instanceof BigDecimal){
			writer.append(formatter.format(num));
		}else{
			writer.append(String.valueOf(num));
		}
	}
}