package simulator.model;

public class CityRoad extends Road{

	CityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	}

	@Override
	void reduceTotalContamination() {
		Weather we = getWeather();		
		if(we == Weather.WINDY || we == Weather.STORM) {
			reduceContamination(10);
		}
		else {
			reduceContamination(2);
		}		
	}

	@Override
	void updateSpeedLimit() {
		//no hace nada, esta de adorno (^3^)
	}

	@Override
	int calculateVehicleSpeed(Vehicle v) {	
		int f = v.getContClass();
		int s = getSpeedLimit(); 	
		return (int)(((11.0-f )/11.0)*s);
	}

}