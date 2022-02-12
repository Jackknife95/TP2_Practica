package simulator.model;

import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class Road extends SimulatedObject {

	private Junction source_junction;
	private Junction destination_junction;
	private int length;
	protected int maximum_speed;
	private int current_speed_limit;
	private int contamination_alarm_limit;
	private int total_contamination;
	protected Weather weather_conditions;
	private List<Vehicle> vehicles;
	
	
	
	Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) { 
		super(id);
		
		if(srcJunc != null && destJunc != null && weather != null) {
			this.source_junction = srcJunc;
			this.destination_junction = destJunc;
			this.weather_conditions = weather;
		}
		else {
			throw new IllegalArgumentException("The arguments must be no null objects");
		}
		
		
		if(maxSpeed >= 0) {
			this.maximum_speed = maxSpeed;
			this.current_speed_limit = maxSpeed;
			
		}
		else {
			throw new IllegalArgumentException("The maximum speed must be a positive Integer");
		}
		
		if(length >= 0) {
			this.length = length;
			
		}
		else {
			throw new IllegalArgumentException("The maximum speed must be a positive Integer");
		}
		
		if(contLimit >= 0) {
			this.contamination_alarm_limit = contLimit;
		}
		else {
			throw new IllegalArgumentException("The maximum speed must be a positive Integer");
		}
	
	}
	
	/*---------------------------------Public Getter Methods-------------------------------------*/
	
	public int getLength() {
		return this.length;
	}
	
	public Junction getDest() {
		return this.destination_junction;
	}
	
	public Junction getSrc() {
		return this.source_junction;
	}
	
	public Weather getWeather() {
		return this.weather_conditions;
	}
	
	public int getContLimit() {
		return this.contamination_alarm_limit;
	}
	
	public int getMaxSpeed() {
		return this.maximum_speed;
	}
	
	public int getTotalCO2() {
		return this.total_contamination;
	}
	
	public int getSpeedLimit() {
		return this.current_speed_limit;
	}
	
	public List<Vehicle> getVehicles(){
		return this.vehicles;
	}

	/*---------------------------------Setter Methods-------------------------------------*/
	
	void setWeather(Weather w) {
		if(w != null) {
			this.weather_conditions = w;
		}
		else {
			throw new IllegalArgumentException("Weather conditions can't be null");
		}
		
	}
	
	void reduceContamination(int c) {			//resta la contaminacion haciendo que nunca sea menor a 0
		this.total_contamination= Math.min(0,this.total_contamination-c);
	}
	
		
	void addContamination(int c) {
			
			if(c >= 0) {
				this.total_contamination = total_contamination + c;
			}
			else{
				throw new IllegalArgumentException("Contamination must be a positive Integer");
			}
		}
	
	protected void setSpeedLimit(int s) {
		this.current_speed_limit= s;
	}
	
	void enter(Vehicle v)  {
		
		if(v.getSpeed() == 0 && v.getLocation() == 0) {
			this.vehicles.add(v);
		}
		else {
			throw new IllegalArgumentException("Vehicle's speed and location must be 0");
		}
		
	}
	
	void exit(Vehicle v) {
		this.vehicles.remove(v);	
	}
	
	@Override
	void advance(int time) {
		reduceTotalContamination();
		updateSpeedLimit();
		
		for(Vehicle v : vehicles) {
			v.setSpeed(calculateVehicleSpeed(v));
			v.advance(time);
		}
			
		Collections.sort(vehicles);
	}

	@Override
	public JSONObject report() {
		
		JSONObject json = new JSONObject();
		JSONArray json_array = new JSONArray(vehicles);
		
		json.put("id", getId());
		json.put("speedLimit", current_speed_limit);
		json.put("weather", weather_conditions);
		json.put("co2", total_contamination);
		json.put("vehicles", json_array);
		
		return json;
	}
	
	abstract void reduceTotalContamination();
	
	abstract void updateSpeedLimit();
	
	abstract int calculateVehicleSpeed(Vehicle v);
	
}