/**
 * 
 */
package org.merecode.json.appenders;

import java.io.IOException;
import java.io.Writer;

import org.merecode.json.Appender;


public class StringAppender implements Appender {
	private boolean escapeForwardSlash;
	private boolean escapeNonAsciiRange;
	public StringAppender(){
	}
	public void setEscapeForwardSlash(boolean escape){
		escapeForwardSlash = escape;
	}
	public void setEscapeNonAsciiRange(boolean escapeNonAsciiRange) {
		this.escapeNonAsciiRange = escapeNonAsciiRange;
	}   
	public void append(Object obj, Writer writer) throws IOException {
		writer.append('"');
		this.escape(obj.toString(), writer);
		writer.append('"');
	}
    private void escape(String string, Writer writer) throws IOException {
        int len = string.length();
        for (int i = 0; i < len; i ++) {
            char c = string.charAt(i);
            switch (c) {
            case '"':
            case '\\':
        		writer.append('\\');
                writer.append(c);
                break;
            case '/':
            	if(escapeForwardSlash){
            		writer.append('\\');
            	}
                writer.append(c);
                break;
            case '\b':
                writer.append("\\b");
                break;
            case '\f':
                writer.append("\\f");
                break;
            case '\n':
                writer.append("\\n");
                break;
            case '\r':
                writer.append("\\r");
                break;
            case '\t':
                writer.append("\\t");
                break;
            default:
                if (escapeNonAsciiRange && (c < '\u0020' || c > '\u007F')){
                	writer.append("\\u");
                	String t = Integer.toHexString(c);
                	int numZeroesNeeded = 4 - t.length();
                	for(int zeroNum=0; zeroNum<numZeroesNeeded; zeroNum++){
                		writer.append('0');
                	}
                    writer.append(t);
                } else {
                    writer.append(c);
                }
            }
        }
    }
}