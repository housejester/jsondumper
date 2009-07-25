package com.itsalleasy.walker;

import javax.sql.DataSource;

public class ClassWithAccessorForNonSerializableReturnType {
	public DataSource getDataSource(){
		return null;
	}
}
