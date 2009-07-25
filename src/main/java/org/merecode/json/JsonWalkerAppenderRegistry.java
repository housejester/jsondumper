package org.merecode.json;

import java.util.Calendar;
import java.util.Date;

import org.merecode.json.appenders.BooleanAppender;
import org.merecode.json.appenders.CalendarAppender;
import org.merecode.json.appenders.DateAppender;
import org.merecode.json.appenders.IgnoringAppender;
import org.merecode.json.appenders.NumberAppender;
import org.merecode.json.appenders.StringAppender;
import org.merecode.json.registries.BasicInheritanceRegistry;


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
