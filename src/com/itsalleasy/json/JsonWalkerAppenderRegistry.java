package com.itsalleasy.json;

import java.util.Calendar;
import java.util.Date;

import com.itsalleasy.json.appenders.BooleanAppender;
import com.itsalleasy.json.appenders.CalendarAppender;
import com.itsalleasy.json.appenders.DateAppender;
import com.itsalleasy.json.appenders.IgnoringAppender;
import com.itsalleasy.json.appenders.NumberAppender;
import com.itsalleasy.json.appenders.StringAppender;
import com.itsalleasy.json.registries.BasicInheritanceRegistry;

public class JsonWalkerAppenderRegistry extends BasicInheritanceRegistry{
	public JsonWalkerAppenderRegistry(){
		super(new IgnoringAppender());
		registerBasicAppenders();
	}

	private void registerBasicAppenders() {
		register(String.class, new StringAppender());
		register(Number.class, new NumberAppender());
		register(Boolean.class, new BooleanAppender());
		register(Calendar.class, new CalendarAppender());
		register(Date.class, new DateAppender());
		register(Character.class, new StringAppender());
		register(Enum.class, new StringAppender());
	}
}
