package simulator.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONObject;

import simulator.misc.SortedArrayList;

public class TrafficSimulator {
	
	private RoadMap mapaCarreteras;
	private List<Event> listaEventos;
	private int time; // Paso de simulación
	
	public TrafficSimulator() { 
		this.mapaCarreteras = new RoadMap();
		this.listaEventos = new SortedArrayList<Event>();
		this.time = 0;
	}
	
	public void addEvent(Event e) {
		listaEventos.add(e);
	}

	
	public void advance() {
		this.time++;
			
		for(Iterator<Event> it = listaEventos.iterator(); it.hasNext();) {
			if(it.next().getTime() == time) {
				it.next().execute(mapaCarreteras);
				it.remove();
			}
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
