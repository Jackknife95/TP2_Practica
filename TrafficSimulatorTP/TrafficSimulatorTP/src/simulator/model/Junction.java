package simulator.model;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class Junction extends SimulatedObject {

	private List<Road> carreterasEntrantes; //Lista de carreteras que entran al cruce
	private Map<Road, List<Vehicle>> listaVehiculo; //Se utiliza para realizar la búsqueda de una cola de forma eficiente (opcional)
	private Map<Junction, Road> carreterasSalientes; //Mapa para saber que carretera tomar para llegar a un determinado cruce
	private List<List<Vehicle>> listaCola;  // Lista de cola para cada carretera entrante
	private int trafficLights; // índice de carretera entrante verde, -1 significa que están todos a rojo
	private int ultimoCambioSemaforo = 0;
	private int x, y;
	private LightSwitchingStrategy lsStrategy;
	private DequeuingStrategy dqStrategy;
	
	
	Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor) { 
		
		super(id);
		
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
		
		carreterasEntrantes.add(r);
		
	}
	
	void addOutGoingRoad(Road r) {		
		carreterasSalientes.put(this,r);
	}
	
	void enter(Vehicle v) {
		//TODO 
	}
	
	Road roadTo(Junction j) {
		return carreterasSalientes.get(j);
	}
	
	
	@Override
	void advance(int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JSONObject report() {
		// TODO Auto-generated method stub
		return null;
	}

}
