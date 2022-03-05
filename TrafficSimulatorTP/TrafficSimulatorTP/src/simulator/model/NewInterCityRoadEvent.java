package simulator.model;

public class NewInterCityRoadEvent extends NewRoadEvent {

	public NewInterCityRoadEvent(int time, String id, String srcJun, String destJunc, int length, int co2Limit, int maxSpeed, Weather weather) {
			super(time,id,srcJun,destJunc, length, co2Limit, maxSpeed, weather);
	}

	@Override
	protected Road createRoad(Junction srcJ, Junction destJ) {	
		return new InterCityRoad(id, srcJ, destJ, maxSpeed, co2Limit, length, weather);
	} 
}