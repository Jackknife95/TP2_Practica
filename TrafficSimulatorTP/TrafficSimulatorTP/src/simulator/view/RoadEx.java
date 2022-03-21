package simulator.view;

public class RoadEx {
	private String _id,_length,_weather;
	private int _co2Limit,_maxSpeed,_speedLimit,_totalCO2;

	public RoadEx(String id , String length,String weather,int maxSpeed,int speedLimit,int totalCO2,int co2Limit) {
		
		_id=id;
		_length=length;
		_weather= weather;
		_maxSpeed=maxSpeed;
		_speedLimit= speedLimit;		
		_totalCO2= totalCO2;
		_co2Limit=co2Limit;
	}

	public String get_id() {
		return _id;
	}

	public String get_length() {
		return _length;
	}

	public String get_weather() {
		return _weather;
	}

	public int get_co2Limit() {
		return _co2Limit;
	}

	public int get_maxSpeed() {
		return _maxSpeed;
	}

	public int get_speedLimit() {
		return _speedLimit;
	}

	public int get_totalCO2() {
		return _totalCO2;
	}
	
}