package simulator.model;

public class InterCityRoad extends Road{

	InterCityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	}
	
	private int calculateWeather(Weather weather) {
		
		int ret;
		
		if(weather.equals(Weather.SUNNY)){
			ret = 2;
		}
		else if (weather.equals(Weather.CLOUDY)) {
			ret = 3;
		}
		else if (weather.equals(Weather.RAINY)) {
			ret = 10;
		}
		else if(weather.equals(Weather.WINDY)) {
			ret = 15;
		}
		else {
			ret = 20;
		}
		
		return ret;
	}

	@Override
	void reduceTotalContamination() {
		int reduced_contamination = (int)(((100 - calculateWeather(getWeather()))/100.0)* getTotalCO2());
		reduceContamination(reduced_contamination);
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
		int vel=getMaxSpeed();							//get de road para velocidad maxima
		if(weather_conditions.equals(Weather.STORM)) {
			vel=(int) (vel*0.8);
		}
		
		return vel;
	}

}
