package simulator.model;

import java.util.ArrayList;
import java.util.List;

import simulator.misc.Pair;

public class SetWeatherEvent extends Event{

	List<Pair<String,Weather>> w;
	
	public SetWeatherEvent(int time, List<Pair<String,Weather>> ws) {		
		super(time);
		if(ws != null) {
			this.w = new ArrayList<Pair<String,Weather>>(ws);			
			/*
			for(Pair<String,Weather> par : ws) {
				w.add(par);
			}
			*/
		}		
		else {		
			throw new IllegalArgumentException("List Pair<String,Weather> is null");
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

	public String toString() {
		return "Weather changed to: ' "+w.get(w.size()-1).getSecond()+" ' in road : " +w.get(w.size()-1).getFirst().toUpperCase();
	}

}
