package com.itsalleasy.json.serializers;

public interface PropertyAccessor {

	public abstract String getName();

	public abstract Object getValue(Object obj);

}