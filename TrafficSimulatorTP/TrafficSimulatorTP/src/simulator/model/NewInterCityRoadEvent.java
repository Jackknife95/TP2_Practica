package simulator.model;

public class NewInterCityRoadEvent extends NewRoadEvent {

	public NewInterCityRoadEvent(int time, String id, String srcJun, String destJunc, int length, int co2Limit, int maxSpeed, Weather weather) {
			super(time,id,srcJun,destJunc, length, co2Limit, maxSpeed, weather);
	}

	@Override
	protected Road createRoad() {
		InterCityRoad r=null;
		if(destJ!= null && srcJ != null) {
			r= new InterCityRoad(id,srcJ,destJ,length,co2Limit,maxSpeed,weather);
		}
		
		return r;
	}
}