package simulator.model;

public class CityRoad extends Road{

	CityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	}

	@Override
	void reduceTotalContamination() {
		// TODO Auto-generated method stub
		String we =getWeather().toString().toUpperCase();
		if(we.equals("WINDY")||we.equals("STORM")) {
			reduceContamination(10);
		}
		else {
			reduceContamination(2);
		}
		
		
	}

	@Override
	void updateSpeedLimit() {
		//no hace nada, esta de adorno :3
	}

	@Override
	int calculateVehicleSpeed(Vehicle v) {
		int f =v.getContClass();
		int s= super.getMaxSpeed();
		return (int)(((11.0-f )/11.0)*s);
	}

}