package simulator.model;

public class InterCityRoad extends Road{

	InterCityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	}
	
	private int calculateWeather(Weather weather) {
			
		if(weather == Weather.SUNNY) {
			return 2;
		}
		else if(weather == Weather.CLOUDY) {
			return 3;
		}
		else if( weather == Weather.RAINY) {
			return 10;
			
		}
		else if (weather == Weather.WINDY) {
			return 15;
		}
		else {
			return 20;
		}
	}

	@Override
	void reduceTotalContamination() {
		
		int x = calculateWeather(getWeather());
		int tc = getTotalCO2();	
		int contamination = (int)(((100 - x)/100.0)* tc);
		reduceContamination(contamination);
	}

	@Override
	void updateSpeedLimit() {
		if(getTotalCO2() > getContLimit()) {
			setSpeedLimit((int)(0.5*getMaxSpeed()));
		}
		else {
			setSpeedLimit(getMaxSpeed());
		}	
	}

	@Override
	int calculateVehicleSpeed(Vehicle v) {
		
		// Se calcula la velocidad más alta posible teniendo en cuenta el limite de velocidad de la carretera
		int vel = getSpeedLimit();				
		if(weather_conditions == Weather.STORM) {
			vel = (int)(vel*0.8);
		}
		
		return vel;
	}

}
