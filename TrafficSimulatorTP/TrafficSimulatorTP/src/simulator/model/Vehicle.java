package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

public class Vehicle extends SimulatedObject implements Comparable<Vehicle>{
	
	private List<Junction> itinerary;			
	
	private int itineraryPos;
	
	private int maximumSpeed;
	
	private int currentSpeed;
	
	private VehicleStatus status;
	
	private Road road;
	
	private int location;
	
	private int contaminationClass;
	
	private int totalContamination;
	
	private int totalTravelledDistance;
	
	Vehicle(String id , int maxSpeed, int contClass, List<Junction> itinerary) {
		super(id);
		
		this.itineraryPos = 0;
		this.currentSpeed = 0;
		this.totalContamination = 0;
		this.totalTravelledDistance = 0;
		this.status = VehicleStatus.PENDING;
		
		if(maxSpeed <= 0 ) {
			throw new IllegalArgumentException("the maximum speed must be a postive Integer");		
		}
		else { 
			this.maximumSpeed = maxSpeed;
		}
					
		if( contClass < 0 || contClass > 10 ) {
			throw new IllegalArgumentException("the contamination class must be a value between 0 and 10 inclusive");		
		}
		else {
			this.contaminationClass = contClass;
		}
		
		if(itinerary.size() < 2) {
			throw new IllegalArgumentException("the itinerary must have at least a size of 2");		
		}
		else {
			this.itinerary = Collections.unmodifiableList(new ArrayList<>(itinerary));
		}
					
	}
	
	/*---------------------------------Setter Methods-------------------------------------*/
	
	public void setSpeed(int s) {
		
		if(s < 0) {
			throw new IllegalArgumentException("the speed must be a positive Integer");		
		}
		else if(status == VehicleStatus.TRAVELING) {
			
			currentSpeed = (Math.min(s, maximumSpeed));
		}	
	}
	
	
	public void setContClass(int c) {
		if ( c < 0 || c > 10 ) {
			throw new IllegalArgumentException("the contamination class must be a value between 0 and 10 inclusive");		
		}
		else {
			this.contaminationClass = c;
		}
	}
	
	
	/*---------------------------------Private Setter Methods-------------------------------------*/

	private void setStatus(VehicleStatus status) {
		this.status = status;
	}

	private void setRoad(Road road) {
		this.road = road;
	}

	private void setLocation(int location) {
		this.location = location;
	}

	private void setTotalTravelledDistance(int totalTravelledDistance) {
		this.totalTravelledDistance = totalTravelledDistance;
	}
	
	
	/*---------------------------------Public Getter Methods-------------------------------------*/
	
	public int getSpeed() {
		return this.currentSpeed;
	}
	
	public int getLocation() {
		return this.location;
	}
	
	public int getMaxSpeed() {
		return this.maximumSpeed;
	}
	
	public int getContClass() {
		return this.contaminationClass;
	}
	
	public VehicleStatus getStatus() {
		return this.status;
	}
	
	public int getTotalCO2(){
		return this.totalContamination;
	}
	
	public List<Junction> getItinerary(){
		return this.itinerary;
	}
	
	public Road getRoad() {
		return this.road;
	}
	/*---------------------------------Default Methods-------------------------------------*/
	
	@Override
	public int compareTo(Vehicle v) {
		
		return v.getLocation() - this.location;
	}
	
	@Override
	void advance(int time) {
		
		int prevLocation = location;

		if(status.equals(VehicleStatus.TRAVELING)) {
			
			setLocation(Math.min(location + currentSpeed, road.getLength()));
			
			int contaminationStep = (location - prevLocation) * contaminationClass;
			
			this.totalContamination += contaminationStep;
			
			this.road.addContamination(contaminationStep);
			
			if(location == road.getLength()) {	
				itineraryPos++;
				itinerary.get(itineraryPos).enter(this);
				setStatus(VehicleStatus.WAITING);
				setSpeed(0);
			}
					
		}
	}
	
	void moveToNextRoad() {
			
		switch(status) {
			case PENDING:		
				this.road = itinerary.get(0).roadTo(itinerary.get(1));
				this.location = 0;
				this.status = VehicleStatus.TRAVELING;
				this.road.enter(this);	
			break;
			
			case WAITING:	
				
				if(itinerary.size() - 1 == itineraryPos) {
					this.road.exit(this);
					this.road = null;
					this.location = 0;
					status = VehicleStatus.ARRIVED;
				}
				else {
					this.road.exit(this);	
					this.road = itinerary.get(itineraryPos).roadTo(itinerary.get(itineraryPos + 1));					
					this.status = VehicleStatus.TRAVELING;
					this.location = 0;
					road.enter(this);					
				}				
			break;
			
			default:
				throw new IllegalArgumentException("The Vehicle status is not PENDING nor WAITING");
		}
	}
	
	@Override
	public JSONObject report() {

		JSONObject json = new JSONObject();
		
		json.put( "id" , getId());
		json.put("speed", currentSpeed);
		json.put("distance",totalTravelledDistance);
		json.put("co2", totalContamination); 
		json.put("class",contaminationClass );
		json.put("status", status);
				
		if(!status.equals(VehicleStatus.PENDING) || !status.equals(VehicleStatus.ARRIVED)) {
			json.put("road", road.getId());
			json.put("location", location);
		}
		
		return json;
	}

}