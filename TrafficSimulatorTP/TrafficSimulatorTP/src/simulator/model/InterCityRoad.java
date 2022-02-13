package simulator.model;

public class InterCityRoad extends Road{

	InterCityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	}
	
	private int calculateWeather(Weather weather) {
		
		int ret;
		
		switch(weather) {
		case SUNNY:
			ret = 2;
			break;
		
		case CLOUDY:
			ret = 3;
			break;
		
		case RAINY:
			ret = 10;
			break;
		
		case WINDY:
			ret = 15;
			break;
			
		case STORM:
			ret = 20;
			break;
			
		default: 
			ret = 1;
			break;
		}
		
		return ret;
	}

	@Override
	void reduceTotalContamination() {
		int contamination = (int)(((100 - calculateWeather(getWeather()))/100.0)* getTotalCO2());
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
		int vel = Math.min(v.getSpeed(), getMaxSpeed());				
		if(weather_conditions.equals(Weather.STORM)) {
			vel = (int)(vel*0.8);
		}
		
		return vel;
	}

}
