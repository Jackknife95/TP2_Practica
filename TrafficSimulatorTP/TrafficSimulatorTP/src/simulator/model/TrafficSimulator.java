package simulator.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.json.JSONObject;

import simulator.misc.SortedArrayList;

public class TrafficSimulator implements Observable<TrafficSimObserver> {
	private List<TrafficSimObserver> observers;
	private RoadMap mapaCarreteras;
	private List<Event> listaEventos;
	private int time; 					// Paso de simulación
	
	public TrafficSimulator() { 
		this.mapaCarreteras = new RoadMap();	
		this.listaEventos = new SortedArrayList<Event>(timeComparator);
		this.time = 0;
		this.observers= new ArrayList<TrafficSimObserver>();
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
		
		for(TrafficSimObserver t : observers) {
			t.onEventAdded(mapaCarreteras, listaEventos, e, time);
		}
		
	}

	
	public void advance() {
		try {
			this.time++;
			
			for(TrafficSimObserver t : observers) {
				t.onAdvanceStart(mapaCarreteras, listaEventos, time);
			}
				
			while(listaEventos.size() > 0 && listaEventos.get(0).getTime() == time) {
				listaEventos.remove(0).execute(mapaCarreteras);
			}
			
			for(Junction j : mapaCarreteras.getJunctions()) {
				j.advance(time);
			}
			
			for(Road r : mapaCarreteras.getRoads()) {
				r.advance(time);
			}	
			
			for(TrafficSimObserver t : observers) {
				t.onAdvanceEnd(mapaCarreteras, listaEventos, time);
			}
		}
		catch(Exception e) {
			for(TrafficSimObserver t : observers) {
				t.onError(e.getMessage());
			}
			//TODO REVISAR POR SI ACASO
			throw e;
		}
	}
	
	public void reset() {
		this.mapaCarreteras.reset();
		this.listaEventos.clear();
		this.time = 0;
		
		for(TrafficSimObserver t : observers) {
			t.onReset(mapaCarreteras, listaEventos, time);
		}
		
	}
	
	public JSONObject report() {
		
		JSONObject jo = new JSONObject();	
		jo.put("time", time);
		jo.put("state", mapaCarreteras.report());		
		
		return jo;
	}

	@Override
	public void addObserver(TrafficSimObserver o) {
		
		observers.add(o);
		
		for(TrafficSimObserver t : observers) {
			t.onRegister(mapaCarreteras, listaEventos, time);
		}
		
	}

	@Override
	public void removeObserver(TrafficSimObserver o) {
		
		observers.remove(o);
		
	}
	
}
