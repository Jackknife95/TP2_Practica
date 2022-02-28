package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
		this.listaCruces = new ArrayList<Junction>();
		this.listaCarreteras = new ArrayList<Road>();
		this.listaVehiculos = new ArrayList<Vehicle>();
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
		
		
		
		String key = j.getId();		
		if(!mapaCruces.containsKey(key)){
			mapaCruces.put(key, j);
			listaCruces.add(j);
		}
		else {
			throw new IllegalArgumentException("Excepción addJunction: cruce ya existente en el mapa de carreteras");
		}
	}
	
	void addRoad(Road r) {
		
		
				
		String id = r.getId();
		
		if(!mapaCarreteras.containsKey(id) && (mapaCruces.containsValue(r.getSrc()) && mapaCruces.containsValue(r.getDest()))) {
			mapaCarreteras.put(id, r);
			listaCarreteras.add(r);
		}
		else {
			throw new IllegalArgumentException("Excepción addRoad: carretera ya existente o cruces no contenidos en el mapa de cruces");
		}
	}
	
	
	void addVehicle(Vehicle v) {
		
		
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
				listaVehiculos.add(v);
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
		
		return mapaCruces.get(s);
	}
	
	
	public Road getRoad(String s) {
		
		return mapaCarreteras.get(s);
	}
	
	public Vehicle getVehicle(String s) {
		
		return mapaVehiculos.get(s);
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
