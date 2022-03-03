package simulator.control;


import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

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
		
		if(sim != null && eventsFactory != null  ){
			this.sim = sim;
			this.eventsFactory = eventsFactory;
			}
		else {
			throw new IllegalArgumentException("Invalid TrafficSimulator or EventsFactory or both");
		}
	}
	
	public void loadEvents(InputStream in) {
		
		JSONObject jo = new JSONObject(new JSONTokener(in));
		
		if(jo.has("events")){
			
			JSONArray ja = jo.getJSONArray("events");
			
			for(Object x: ja) {
				JSONObject jo2 = (JSONObject) x;
				sim.addEvent(eventsFactory.createInstance(jo2));
			}
			
		}		
		else {
			throw new IllegalArgumentException("Invalid input stream structure");
		}
	}
	
	public void run(int n, OutputStream out) {
			
		PrintStream p = new PrintStream(out);
		
		p.println("{");
		p.println( "\"states\" :[");
		
		for(int i = 0; i < n; i++) {
			sim.advance();
			p.print(sim.report());
			if (i != n-1) {
				p.println(",");
			}			
		}
		p.println("]}");		
		p.close();
	}
	
	public void reset() {
		sim.reset();
	}
}
