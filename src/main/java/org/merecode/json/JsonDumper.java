package org.merecode.json;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.merecode.json.appenders.PreciseNumberAppender;
import org.merecode.json.appenders.RawNumberAppender;
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
	public static final TrackingPolicy DEFAULT_TRACKING_POLICY = TrackingPolicies.TRACK_OBJECTS_AND_PATHS;
	public static final NodeWalkerFactory DEFAULT_WALKER_FACTORY = new BasicNodeWalkerFactory();
	
	private Walker walker;
	private AppenderRegistry appenders;
	private AppenderRegistry customAppenders;
	private JsonWalkerAppenderRegistry baseAppenders;
	private PropertyFilter customFilter;
	private TrackingPolicy trackingPolicy;
	private NodeWalkerFactory walkerFactory;

	private int minDecimalDigits = -1;
	private int maxDecimalDigits = -1;
	private boolean skipNulls = true;
	private boolean skipEmptyCollections = true;
	
	public JsonDumper(){
		this(null, null, null, null);
	}

	public JsonDumper(PropertyFilter filter, TrackingPolicy trackingPolicy, AppenderRegistry customAppenders, NodeWalkerFactory walkerFactory){
		this.baseAppenders = new JsonWalkerAppenderRegistry();
		this.customAppenders = customAppenders;
		this.customFilter = filter;
		this.appenders = combineAppenders();
		this.trackingPolicy = trackingPolicy == null ? DEFAULT_TRACKING_POLICY : trackingPolicy;
		this.walkerFactory = walkerFactory == null ? DEFAULT_WALKER_FACTORY : walkerFactory;
		resetWalker();
	}
	
	private void resetWalker() {
		walker = new Walker(createFilter(), trackingPolicy, walkerFactory);
	}

	private PropertyFilter createFilter() {
		List<PropertyFilter> filters = getLegacyDefaultFilters();

		if(skipNulls){
			filters.add(PropertyFilters.IS_NULL);
		}
		if(skipEmptyCollections){
			filters.add(PropertyFilters.IS_EMPTY_COLLECTION);
		}
		
		if(customFilter != null){
			filters.add(customFilter);
		}

		if(filters.isEmpty()){
			return PropertyFilters.FILTER_NONE;
		}
		return PropertyFilters.chain(filters.toArray(new PropertyFilter[filters.size()]));
	}

	private List<PropertyFilter> getLegacyDefaultFilters() {
		/* Just here until all filters are configurable via setSkip* methods */
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		
		filters.add(PropertyFilters.IS_FALSE);
		filters.add(PropertyFilters.IS_EMPTY_STRING);
		filters.add(PropertyFilters.IS_ZERO);
		filters.add(PropertyFilters.IS_EMPTY_ARRAY);
		filters.add(PropertyFilters.IS_EMPTY_MAP);

		return filters;
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
		this.maxDecimalDigits = maxDigits;
		resetNumberAppender();
	}

	private void resetNumberAppender() {
		Appender appender = null;
		if(maxDecimalDigits == -1 && minDecimalDigits == -1){
			appender = new RawNumberAppender();
		}else{
			appender = new PreciseNumberAppender(minDecimalDigits, maxDecimalDigits);
		}
		baseAppenders.register(Number.class, appender);
		combineAppenders();
	}

	public void setMinDecimalDigits(int minDigits) {
		this.minDecimalDigits = minDigits;
		resetNumberAppender();
	}

	public void setSkipNulls(boolean skip) {
		skipNulls = skip;
		resetWalker();
	}

	public void setSkipEmptyCollections(boolean skip) {
		skipEmptyCollections = skip;
		resetWalker();
	}
}
