package simulator.model;

import java.util.LinkedList;
import java.util.List;

import simulator.misc.Pair;

public class SetWeatherEvent extends Event{

	List<Pair<String,Weather>> w;
	
	SetWeatherEvent(int time, List<Pair<String,Weather>> ws) {
		
		super(time);
		
		if(ws != null) {
			w = new LinkedList<Pair<String,Weather>>();
			
			for(Pair<String,Weather> par : ws) {
				w.add(par);
			}
			
		}		
		else {		
			throw new IllegalArgumentException("Invalid List of Pair<String,Weather>");
		}
		
	}

	@Override
	void execute(RoadMap map) {
				
		for(Pair<String,Weather> par : w) {
			
			Road r = map.getRoad(par.getFirst());
			
			if (r == null) {
				throw new IllegalArgumentException("Invalid Road to set Weather");
			}			
			else {
				r.setWeather(par.getSecond());
			}			
		}		
	}

}
