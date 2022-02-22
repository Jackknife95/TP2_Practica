package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class RoadMap {
	
	private List<Junction>listaCruces;
	private List<Road>listaCarreteras;
	private List<Vehicle> listaVehiculos;
	private Map<String,Junction> mapaCruces;
	private Map<String,Road>mapaCarreteras;
	private Map<String,Vehicle> mapaVehiculos;
	
	public RoadMap(){
		this.listaCruces = new LinkedList<Junction>();
		this.listaCarreteras = new LinkedList<Road>();
		this.listaVehiculos = new LinkedList<Vehicle>();
		this.mapaCruces = new HashMap<String, Junction>();
		this.mapaCarreteras = new HashMap<String, Road>();
		this.mapaVehiculos = new HashMap<String, Vehicle>();
	}
	
	void reset() {	
		listaCruces.clear();
		listaCarreteras.clear();
		listaVehiculos.clear();	
		mapaCruces.clear();
		mapaCarreteras.clear();
		mapaVehiculos.clear();
	}
	
	void addJunction(Junction j) {
		
		listaCruces.add(j);
		
		String key = j.getId();		
		if(!mapaCruces.containsKey(key)){
			mapaCruces.put(key, j);
		}
		else {
			throw new IllegalArgumentException("Excepción addJunction: cruce ya existente en el mapa de carreteras");
		}
	}
	
	void addRoad(Road r) {
		
		listaCarreteras.add(r);
				
		String id = r.getId();
		
		if(!mapaCarreteras.containsKey(id) && mapaCruces.containsValue(r.getSrc()) && mapaCruces.containsValue(r.getDest())) {
			mapaCarreteras.put(id, r);
		}
		else {
			throw new IllegalArgumentException("Excepción addRoad: carretera ya existente o cruces no contenidos en el mapa de cruces");
		}
	}
	
	
	void addVehicle(Vehicle v) {
		listaVehiculos.add(v);
		
		String id = v.getId();
		
		if(!mapaVehiculos.containsKey(id)) {
			
			int i = 0;
			boolean validItinerary = true;
			List<Junction> itinerary = new ArrayList<Junction>(v.getItinerary());
			
			while ((i < (itinerary.size() - 1)) && validItinerary) {
				
				if(itinerary.get(i).roadTo(itinerary.get(i+1)) == null) {
					validItinerary = false;
				}				
				i++;
			}
			
			if(validItinerary) {
				mapaVehiculos.put(id, v);
			}
			else {
				throw new IllegalArgumentException("Excepción addVehicle: el itinerario es inválido");
			}
			
		}
		else {
			throw new IllegalArgumentException("Excepción addVehicle: ya existe un vehículo con ese identificador");
		}
			
	}
	
	
	public Junction getJunction(String s) {
		
		if(mapaCruces.containsKey(s)) {
			return mapaCruces.get(s);
		}
		
		return null;
	}
	
	
	public Road getRoad(String s) {
		
		if(mapaCarreteras.containsKey(s)) {
			return mapaCarreteras.get(s);
		}

		return null;
	}
	
	public Vehicle getVehicle(String s) {
		
		if(mapaVehiculos.containsKey(s)) {
			return mapaVehiculos.get(s);
		}
		
		return null;
	}
	
	public List<Vehicle> getVehicles(){
		
		return Collections.unmodifiableList(new ArrayList<Vehicle>(listaVehiculos));
	}
	
	public List<Road> getRoads(){

		return Collections.unmodifiableList(new ArrayList<Road>(listaCarreteras));
	}
	
	public List<Junction> getJunctions(){

		return Collections.unmodifiableList(new ArrayList<Junction>(listaCruces));
	}
	
	
	public JSONObject report() {
		JSONObject jo = new JSONObject();	
		JSONArray ja = new JSONArray();
		
		for(Junction j : listaCruces) {
			ja.put(j.report());
		}		
		jo.put("junctions",ja);
		
		
		ja= new JSONArray();
		
		for(Road r : listaCarreteras) {
			ja.put(r.report());
		}		
		jo.put("road", ja);
		
		
		ja= new JSONArray();
		
		for(Vehicle v : listaVehiculos) {
			ja.put(v.report());
		}
		jo.put("vehicles", ja);
		
		return jo;
	}
}
