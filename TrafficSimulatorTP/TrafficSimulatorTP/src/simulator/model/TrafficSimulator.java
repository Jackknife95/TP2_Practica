package simulator.model;

import java.util.Comparator;
import java.util.List;

import org.json.JSONObject;

import simulator.misc.SortedArrayList;

public class TrafficSimulator {
	
	private RoadMap mapaCarreteras;
	private List<Event> listaEventos;
	private int time; 					// Paso de simulación
	
	public TrafficSimulator() { 
		this.mapaCarreteras = new RoadMap();	
		this.listaEventos = new SortedArrayList<Event>(timeComparator);
		this.time = 0;
	}
	
	static Comparator<Event> timeComparator = new Comparator<Event>() {
		@Override
		public int compare(Event e1, Event e2) {
			if(e1.getTime() > e2.getTime()) {
				return 1;
			}
			else if (e1.getTime() < e2.getTime()) {
				return -1;
			}		
			return 0;
		}
	};
	
	public int getTime() {
		return this.time;
	}
	
	public void addEvent(Event e) {
		listaEventos.add(e);
	}

	
	public void advance() {
		this.time++;
			
		while(listaEventos.size() > 0 && listaEventos.get(0).getTime() == time) {
			listaEventos.remove(0).execute(mapaCarreteras);
		}
		
		for(Junction j : mapaCarreteras.getJunctions()) {
			j.advance(time);
		}
		
		for(Road r : mapaCarreteras.getRoads()) {
			r.advance(time);
		}	
	}
	
	public void reset() {
		this.mapaCarreteras.reset();
		this.listaEventos.clear();
		this.time = 0;
	}
	
	public JSONObject report() {
		
		JSONObject jo = new JSONObject();	
		jo.put("time", time);
		jo.put("state", mapaCarreteras.report());		
		
		return jo;
	}
	
}
