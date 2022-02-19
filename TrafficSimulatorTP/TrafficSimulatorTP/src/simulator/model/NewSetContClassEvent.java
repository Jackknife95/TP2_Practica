package simulator.model;

import java.util.LinkedList;
import java.util.List;

import simulator.misc.Pair;

public class NewSetContClassEvent extends Event{

	List<Pair<String,Integer>> c;
	
	NewSetContClassEvent(int time, List<Pair<String,Integer>> cs){
		super(time);
		
		if(cs != null) {			
			c = new LinkedList<Pair<String,Integer>>();			
			for( Pair< String, Integer> par : cs) {
				c.add(par);
			}
		}		
		else {
			throw new IllegalArgumentException("Invalid List of Pair<String,Integer>");
		}
	}

	@Override
	void execute(RoadMap map) {
		
		for(Pair<String,Integer> par : c) {
			
			Vehicle v =	map.getVehicle(par.getFirst());
			
			if (v == null) {
				throw new IllegalArgumentException("Invalid Vehicle to set ContClass");
			}
			else {
				v.setContClass(par.getSecond());
			}			
		}
	}

}
