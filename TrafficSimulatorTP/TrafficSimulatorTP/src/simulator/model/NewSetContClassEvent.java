package simulator.model;

import java.util.ArrayList;
import java.util.List;

import simulator.misc.Pair;

public class NewSetContClassEvent extends Event{

	List<Pair<String,Integer>> c;
	
	public NewSetContClassEvent(int time, List<Pair<String,Integer>> cs){
		super(time);
		
		if(cs != null) {			
			this.c = new ArrayList<Pair<String,Integer>>(cs);		
			/*
			for( Pair< String, Integer> par : cs) {
				c.add(par);
			}
			*/
		}		
		else {
			throw new IllegalArgumentException("List Pair<String,Integer> is null");
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
	

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("Change CO2 class: ");
		int i= 0;
		s.append("[");
		for(Pair<String,Integer> par : c) {
			
			s.append("(").append(par.getFirst().toUpperCase()).append(", ").append(par.getSecond()).append(")");
			
			if(i<c.size()-1) {
				s.append(" , ");
			}
			i++;
		}
	
		s.append("]");
		return s.toString();
	}
	
}
