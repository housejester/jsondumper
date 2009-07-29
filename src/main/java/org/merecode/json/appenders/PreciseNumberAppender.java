/**
 * 
 */
package org.merecode.json.appenders;

import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.merecode.json.Appender;


public class PreciseNumberAppender implements Appender {
    private DecimalFormat formatter;
    
    public PreciseNumberAppender(){
    	formatter = new DecimalFormat();
		formatter.setMinimumFractionDigits(1);
		formatter.setGroupingUsed(false);
   	};

   	public PreciseNumberAppender(int minDecimalDigits, int maxDecimalDigits) {
   		this();
   		if(minDecimalDigits >= 0){
   			formatter.setMinimumFractionDigits(minDecimalDigits);
   		}
   		if(maxDecimalDigits >= 0){
   			formatter.setMaximumFractionDigits(maxDecimalDigits);
   		}
	}

	public void append(Object obj, Writer writer) throws IOException {
		Number num = (Number)obj;
		if(num instanceof Float || num instanceof Double || num instanceof BigDecimal){
			writer.append(formatter.format(num));
		}else{
			writer.append(String.valueOf(obj));
		}
	}
}