package outworldmind.owme.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public class EventsManager {
	
	private Map<String, Map<Object, Event>> events;
	
	protected EventsManager() {
		events = new HashMap<String, Map<Object, Event>>();
	}
	
	public void bind(String eventName, Object object, BiFunction<Object, Object, Void> onDispatch) {
		var eventMap = findEventMap(eventName);
		var event = findEvent(object, eventMap);	
		event.addFunction(onDispatch);	
	}
	
	private Map<Object, Event> findEventMap(String eventName) {
		Map<Object, Event> eventMap;
		if (events.containsKey(eventName)) {
			eventMap =  events.get(eventName);
		} else {
			eventMap = new HashMap<Object, Event>();
			events.put(eventName, eventMap);
		}
		
		return eventMap;
	}
	
	private Event findEvent(Object object, Map<Object, Event> eventMap) {
		Event event;
		if (eventMap.containsKey(object)) {
			event = eventMap.get(object);
		} else {
			event = new Event(object);
			eventMap.put(object, event);
		}
		return event;
	}
	
	public void send(String eventName, Object data) {
		if (!events.containsKey(eventName)) return;
		events.get(eventName).values().stream().forEach(event -> {
			var functions = event.getFunctions();
			var object = event.getObject();
			functions.forEach(function -> function.apply(object, data));
		});
	}

}

class Event {
	
	private Object object;
	private List<BiFunction<Object, Object, Void>> functions;
	
	public Event(Object object) {
		this.object = object;
		functions = new ArrayList<BiFunction<Object, Object, Void>>();
	}
	
	public Event addFunction(BiFunction<Object, Object, Void> function) {
		functions.add(function);
		
		return this;
	}
	
	public List<BiFunction<Object, Object, Void>> getFunctions() {
		return functions;
	}
	
	public Object getObject() {
		return object;
	}
}
