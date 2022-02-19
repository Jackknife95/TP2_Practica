package simulator.model;

import java.util.LinkedList;
import java.util.List;

public class NewVehicleEvent extends Event {
	
	private String id;
	private int maxSpeed, contClass;
	private List<String> itinerary;
	
	NewVehicleEvent(int time, String id, int maxSpeed, int contClass, List<String> itinerary) {
		super(time);
		this.id = id;
		this.maxSpeed = maxSpeed;
		this.contClass = contClass;
		this.itinerary = itinerary;
	}

	@Override
	void execute(RoadMap map) {
		
		
		List<Junction> listJun = new LinkedList<Junction>();
		for(String s: itinerary) {
			Junction j = map.getJuction(s);
			if(j != null) {
				listJun.add(j);
			}
		}
		
		Vehicle v = new Vehicle(id, maxSpeed, contClass, listJun);
		map.addVehicle(v);
		v.moveToNextRoad();	
					
	}

}
