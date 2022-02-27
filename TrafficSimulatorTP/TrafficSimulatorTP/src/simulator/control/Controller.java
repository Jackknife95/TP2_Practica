package simulator.control;


import java.io.InputStream;
import java.io.OutputStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.TrafficSimulator;

public class Controller {
	private TrafficSimulator sim;
	private Factory<Event> eventsFactory ;
	public Controller(TrafficSimulator sim, Factory<Event> eventsFactory) {
		if(sim!=null && eventsFactory!=null  ){
			this.sim= sim;
			this.eventsFactory= eventsFactory;
			}
		else {
			throw new IllegalArgumentException("Invalid TrafficSimulator or Invalid EventsFactory or bolth");
		}
	}
	
	public void loadEvents(InputStream in) {
		
		JSONObject jo = new JSONObject(new JSONTokener(in));
		
		if(jo.has("events")){
			
			JSONArray ja= jo.getJSONArray("events");
			
			for(Object x: ja) {
				JSONObject jo2=(JSONObject) x;
				sim.addEvent(eventsFactory.createInstance(jo2));
			}
			
		}
		
		else {
			throw new IllegalArgumentException("Invalid input stream structure");
		}
	}
	
	public void run(int n, OutputStream out) {
		
		JSONArray ja=new JSONArray();
		
		for(int i = 0; i<= n; i++) {
			sim.advance();
			ja.put(sim.report());
		}
		
		JSONObject jo = new JSONObject();
		
		jo.put("states", ja);
		
	}
	
	public void reset() {
		sim.reset();
	}
}
