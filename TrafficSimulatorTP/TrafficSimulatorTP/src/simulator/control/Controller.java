package simulator.control;

import java.io.InputStream;
import java.io.OutputStream;

import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.TrafficSimulator;

public class Controller {
	private TrafficSimulator trafficSimulator;
	private Factory<Event> eventsFactory;
	
	public Controller(TrafficSimulator sim, Factory<Event> eventsFactory) { 
		
		if(sim != null && eventsFactory != null) {
			this.trafficSimulator = sim;
			this.eventsFactory = eventsFactory;
		}
		else {
			throw new IllegalArgumentException("Controller Arguments are null");
		}
	}
	
	public void loadEvents(InputStream in) {
		JSONObject jo = new JSONObject(new JSONTokener(in)); 
		//TODO

	}
	
	public void run(int n, OutputStream out) {
		//TODO
		
	}
	
	public void reset() {
		trafficSimulator.reset();
	}
	
}
