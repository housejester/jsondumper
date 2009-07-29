package org.merecode.json;

import java.io.IOException;
import java.io.Writer;

import org.merecode.json.appenders.PreciseNumberAppender;
import org.merecode.json.appenders.StringAppender;
import org.merecode.json.registries.CachingAppenderRegistry;
import org.merecode.json.registries.CombinedAppenderRegistry;
import org.merecode.json.registries.NullCheckRegistry;
import org.merecode.walker.BasicNodeWalkerFactory;
import org.merecode.walker.NodeWalkerFactory;
import org.merecode.walker.PropertyFilter;
import org.merecode.walker.PropertyFilters;
import org.merecode.walker.TrackingPolicies;
import org.merecode.walker.TrackingPolicy;
import org.merecode.walker.Walker;

public class JsonDumper extends AbstractJsonSerializer {
	public static final PropertyFilter DEFAULT_FILTER = PropertyFilters.IS_DEFAULT_OR_EMPTY;
	public static final TrackingPolicy DEFAULT_TRACKING_POLICY = TrackingPolicies.TRACK_OBJECTS_AND_PATHS;
	public static final NodeWalkerFactory DEFAULT_WALKER_FACTORY = new BasicNodeWalkerFactory();
	
	private Walker walker;
	private AppenderRegistry appenders;
	private AppenderRegistry customAppenders;
	private JsonWalkerAppenderRegistry baseAppenders;
	
	public JsonDumper(){
		this(null, null, null, null);
	}

	public JsonDumper(PropertyFilter filter, TrackingPolicy trackingPolicy, AppenderRegistry customAppenders, NodeWalkerFactory walkerFactory){
		this.baseAppenders = new JsonWalkerAppenderRegistry();
		this.customAppenders = customAppenders;
		this.appenders = combineAppenders();
		walker = new Walker(
				filter == null ? DEFAULT_FILTER : filter,
				trackingPolicy == null ? DEFAULT_TRACKING_POLICY : trackingPolicy,
				walkerFactory == null ? DEFAULT_WALKER_FACTORY : walkerFactory
		);
	}
	
	private AppenderRegistry combineAppenders(){
		AppenderRegistry target = this.baseAppenders;
		if(this.customAppenders != null){
			target = new CombinedAppenderRegistry(this.customAppenders, this.baseAppenders);
		}
		return new NullCheckRegistry(new CachingAppenderRegistry(target));
	}

	public void serialize(Object obj, Writer writer) throws IOException {
		try{
			walker.walk(obj, new JsonWalkerVisitor(writer, appenders));
		}catch(JsonIOException e){
			throw e.getIOException();
		}
	}

	public void setEscapeForwardSlashes(boolean escapeForwardSlashes) {
		StringAppender stringAppender = new StringAppender();
		stringAppender.setEscapeForwardSlash(escapeForwardSlashes);
		baseAppenders.register(String.class, stringAppender);
		combineAppenders();
	}
	
	public void setEscapeNonAsciiRange(boolean escapeNonAsciiRange){
		StringAppender stringAppender = new StringAppender();
		stringAppender.setEscapeNonAsciiRange(escapeNonAsciiRange);
		baseAppenders.register(String.class, stringAppender);
		combineAppenders();
	}

	public void setMaxDecimalDigits(int maxDigits) {
		PreciseNumberAppender numberAppender = new PreciseNumberAppender();
		numberAppender.setMaxDecimalDigits(maxDigits);
		baseAppenders.register(Number.class, numberAppender);
		combineAppenders();
	}
}
