package org.merecode.json;

import java.io.IOException;

public class JsonIOException extends RuntimeException {
	public JsonIOException(IOException ex){
		super(ex);
	}
	public IOException getIOException(){
		return (IOException)getCause();
	}
}
