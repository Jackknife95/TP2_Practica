package simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class Junction extends SimulatedObject {

	private List<Road> carreterasEntrantes; //Lista de carreteras que entran al cruce
	private Map<Road, List<Vehicle>> mapa_carretera_cola; // Se utiliza para realizar búsquedas rápidas
	private Map<Junction, Road> carreterasSalientes; // Carretera tomada para llegar a un junction desde la actual
	private List<List<Vehicle>> lista_cola;  // Lista de cola para carreteras entrantes
	private int currGreen; // índice de carretera entrante con semáforo en verde (-1 todos en rojo)
	private int lastSwitchingTime = 0; // Ultimo paso que paso de verde a rojo
	private int x, y;
	private LightSwitchingStrategy lsStrategy;
	private DequeuingStrategy dqStrategy;
	
	
	Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor) { 
		
		super(id);
		
		this.carreterasEntrantes = new ArrayList<Road>();
		this.mapa_carretera_cola = new HashMap<Road, List<Vehicle>>();
		this.carreterasSalientes = new HashMap<Junction, Road>();
		this.lista_cola = new ArrayList<List<Vehicle>>();
		
		if(lsStrategy != null && dqStrategy != null && xCoor >= 0 && yCoor >= 0) {

			this.lsStrategy = lsStrategy;
			this.dqStrategy = dqStrategy;
			this.x = xCoor;
			this.y = yCoor;
		}
		else {
			throw new IllegalArgumentException("Arguments can't be null or negative values");
		}
		
	}
	
	void addIncommingRoad(Road r) {		
				
		if(r.getDest() == this) { // Se comprueba que realmente es una carretera entrante
			
			carreterasEntrantes.add(r); // Insertar al final la lista de carreteras entrantes
			List<Vehicle> list = new LinkedList<Vehicle>(); // Creamos una nueva instancia de una Lista enlazada
			lista_cola.add(list); // Insertar la linkedList al final de la cola de vehiculos
			mapa_carretera_cola.put(r, list); // Insertamos en el mapa de carretera - lista vehiculos para posteriormente realizar una busqueda eficiente
		}
		else {
			throw new IllegalArgumentException("addIncommingRoad recibe una carretera que no es entrante");
		}
		
	}
	
	void addOutGoingRoad(Road r) {	
		
		Junction j = r.getDest();

		// Comprueba que ninguna carretera va al cruce destino de la carretera r y que es una carretera saliente del cruce actual
		if(carreterasSalientes.get(j) == null && r.getSrc() == this) { 
			carreterasSalientes.put(j,r);
		}
		else {
			throw new IllegalArgumentException("addOutGoingRoad recibe una carretera invalida");
		}
	}
	
	void enter(Vehicle v) {
		Road currentRoad = v.getRoad();
		
		// Insertamos en la cola correspondiente
		int index = carreterasEntrantes.indexOf(currentRoad);
		lista_cola.get(index).add(v);
		
		// Insertamos en el mapa la misma información
		mapa_carretera_cola.get(currentRoad).add(v);
	}
	
	
	Road roadTo(Junction j) {		
		return carreterasSalientes.get(j);
	}
	
	
	@Override
	void advance(int time) {
		
		Road key = null;
		
		for(List<Vehicle> q : lista_cola) {

			for(Road r : carreterasEntrantes) {
				if(mapa_carretera_cola.get(r).equals(q)) {
					key = r;
				}
			}
					
			List<Vehicle> temp = dqStrategy.dequeue(q); // Cola de vehiculos a quitar 
					
			for(Vehicle v : temp) { // Quita los vehiculos tanto de la lista como del mapa
				v.advance(time);
				q.remove(v); 
				
				if(key != null) {
					mapa_carretera_cola.get(key).remove(v);
				}			
			}
		}
		
		
		int index = lsStrategy.chooseNextGreen(carreterasEntrantes, lista_cola, currGreen, lastSwitchingTime, time);
		
		
		if(index != currGreen) {
			this.currGreen = index;
			this.lastSwitchingTime = time;
		}
	}

	@Override
	public JSONObject report() {
		
		JSONObject json = new JSONObject();
		
		JSONArray queues = new JSONArray();
		
		String aux = currGreen == -1 ? "none" : carreterasEntrantes.get(currGreen).getId();	
		
		json.put("id", getId());
		json.put("green", aux);
		
		for(int i = 0; i < lista_cola.size(); i++) {
					
			String id = carreterasEntrantes.get(i).getId();
				
			JSONArray vehicles = new JSONArray(); // Lista de vehiculos de la cola ordenada
			for(int j = 0; j < lista_cola.get(i).size(); j++) {
				vehicles.put(lista_cola.get(i).get(j));
			}
			
			JSONObject json2 = new JSONObject();
			json2.put("road", id);
			json2.put("vehicles", vehicles);		
			
			queues.put(json2);		
		}
		
		json.put("queues", queues);
			
		return json;
	}

}
