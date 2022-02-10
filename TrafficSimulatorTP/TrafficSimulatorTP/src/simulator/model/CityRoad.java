package simulator.model;

public class CityRoad extends Road{

	CityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	}

	@Override
	void reduceTotalContamination() {
		// TODO Auto-generated method stub
		String we =super.getWeather().toString().toUpperCase();
		int cont = super.getTotalContamination();
		if(we.equals("WINDY")||we.equals("STORM")) {
			super.setTotalContamination(Math.min(cont-10, 0));
		}
		else {
			super.setTotalContamination(Math.min(cont-2, 0));
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