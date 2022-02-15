package simulator.model;

import java.util.LinkedList;
import java.util.List;

public class NewVehicleEvent extends Event {
	
	private String id;
	private int maxSpeed, contClass;
	private List<String> itinerary;
	
	NewVehicleEvent(int time, String id, int maxSpeed, int contClass, List<String> itinerary) {
		super(time);
		// TODO Auto-generated constructor stub
		this.id= id;
		this.maxSpeed=maxSpeed;
		this.contClass=contClass;
		this.itinerary=itinerary;
	}

	@Override
	void execute(RoadMap map) {
		// TODO Auto-generated method stub
		if(map.getVehicle(id)==null) {
			List<Junction> listJun= new LinkedList<Junction>();
			for(int i=0;i<map.getJunctions().size();i++) {
				Junction j=map.getJuction(itinerary.get(i));
					if(j!= null) {
						listJun.add(j);
					}
					else {
						//TODO exception "ALARMA, ITINERARIO NO VALIDO XD"
					}
			}
			Vehicle v = new Vehicle(id, maxSpeed, contClass,listJun);
			map.addVehicle(v);
			v.moveToNextRoad();
			
		}
	}

}
