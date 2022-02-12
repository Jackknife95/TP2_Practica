package simulator.model;

import java.util.Collections;
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
		List<Junction>listaCruces=this.listaCruces;
		List<Road>listaCarreteras=this.listaCarreteras;
		List<Vehicle> listaVehiculos=this.listaVehiculos;
		Map<String,Junction> mapaCruces=this.mapaCruces;
		Map<String,Road>mapaCarreteras=this.mapaCarreteras;
		Map<String,Vehicle> mapaVehiculos=this.mapaVehiculos;
	}
	
	void reset() {
		listaCruces.removeAll(listaCruces);
		listaCarreteras.removeAll(listaCarreteras);
		listaVehiculos.removeAll(listaVehiculos);
		
		mapaCruces.clear();
		mapaCarreteras.clear();
		mapaVehiculos.clear();
	}
	
	void addJunction(Junction j) {
		listaCruces.add(j);
	}
	
	void addRoad(Road r) {
		listaCarreteras.add(r);
	}
	
	void addVehicle(Vehicle v) {
		listaVehiculos.add(v);
	}
	
	public Vehicle getVehicle(String s) {
		return mapaVehiculos.get(s);
	}
	
	public List<Vehicle> getVehicles(){
		List<Vehicle> lv = listaVehiculos;
		return Collections.unmodifiableList(lv);
	}
	
	public Road getRoad(String s) {
		return mapaCarreteras.get(s);
	}
	
	public List<Road> getRoads(){
		List<Road> lr = listaCarreteras;
		return Collections.unmodifiableList(lr);
	}
	
	public Junction getJuction(String s) {
		return mapaCruces.get(s);
	}
	
	public List<Junction> getJunctions(){
		List<Junction> lj = listaCruces;
		return Collections.unmodifiableList(lj);
	}
	
	public JSONObject report() {
		JSONObject jo = new JSONObject();
		
		JSONArray ja = new JSONArray();
		
		for(Junction x : listaCruces) {
			ja.put(x.report());
		}
		
		jo.put("junctions",ja);
		
		ja= new JSONArray();
		
		for(Road x : listaCarreteras) {
			ja.put(x.report());
		}
		
		jo.put("road", ja);
		
		ja= new JSONArray();
		
		for(Vehicle x : listaVehiculos) {
			ja.put(x.report());
		}
		jo.put("vehicles", ja);
		
		return jo;
	}
}
