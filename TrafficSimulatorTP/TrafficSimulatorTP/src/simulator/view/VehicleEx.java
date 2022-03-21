package simulator.view;

public class VehicleEx {
	private String _id,_location,_itinerary;
	private int _co2class,_maxSpeed,_speed,_totalCO2,_distance;

	public VehicleEx(String id , String location,String itinerary,int co2class,int maxSpeed,int speed,int totalCO2,int distance) {
		
		_id=id;
		_location=location;
		_itinerary= itinerary;
		_co2class=co2class;
		_maxSpeed=maxSpeed;
		_speed= speed;
		_totalCO2= totalCO2;
		_distance= distance;
	}
	
	public String get_id() {
		return _id;
	}

	public String get_location() {
		return _location;
	}

	public String get_itinerary() {
		return _itinerary;
	}

	public int get_co2class() {
		return _co2class;
	}

	public int get_maxSpeed() {
		return _maxSpeed;
	}

	public int get_speed() {
		return _speed;
	}

	public int get_totalCO2() {
		return _totalCO2;
	}

	public int get_distance() {
		return _distance;
	}

}