package com.itsalleasy.walker;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

public class PropertyFilters {
	public static final PropertyFilter IS_NULL = new PropertyFilter(){
		public boolean matches(Object value, String name) {
			return value == null;
		}
	};
	public static final PropertyFilter IS_EMPTY_STRING = new PropertyFilter(){
		public boolean matches(Object value, String name) {
			return "".equals(value);
		}
	};
	private static final Integer ZERO = new Integer(0);
	public static final PropertyFilter IS_ZERO = new PropertyFilter(){
		public boolean matches(Object value, String name) {
			return ZERO.equals(value);
		}
	};
	public static final PropertyFilter IS_FALSE = new PropertyFilter(){
		public boolean matches(Object value, String name) {
			return Boolean.FALSE.equals(value);
		}
	};
	public static final PropertyFilter IS_EMPTY_COLLECTION = new PropertyFilter(){
		@SuppressWarnings("unchecked")
		public boolean matches(Object value, String name) {
			return (value instanceof Collection) && ((Collection)value).isEmpty();
		}
	};
	public static final PropertyFilter IS_EMPTY_ARRAY = new PropertyFilter(){
		public boolean matches(Object value, String name) {
			return value != null && value.getClass().isArray() && Array.getLength(value) == 0;
		}
	};
	public static final PropertyFilter IS_EMPTY_MAP = new PropertyFilter(){
		@SuppressWarnings("unchecked")
		public boolean matches(Object value, String name) {
			return (value instanceof Map) && ((Map)value).isEmpty();
		}
	};
	public static final PropertyFilter chain(final PropertyFilter ... filters){
		return new PropertyFilter(){
			public boolean matches(Object value, String name) {
				for(PropertyFilter filter : filters){
					if(filter.matches(value, name)){
						return true;
					}
				}
				return false;
			}
		};
	}
	public static final PropertyFilter FILTER_NONE = new PropertyFilter(){
		public boolean matches(Object value, String name) {
			return false;
		}
	};

	public static final PropertyFilter IS_DEFAULT_OR_EMPTY = chain(
		IS_NULL, IS_FALSE, IS_EMPTY_STRING, IS_ZERO, IS_EMPTY_COLLECTION, IS_EMPTY_ARRAY, IS_EMPTY_MAP
	);
}
