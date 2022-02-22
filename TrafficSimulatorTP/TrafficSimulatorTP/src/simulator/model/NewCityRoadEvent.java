package simulator.model;

public class NewCityRoadEvent extends NewRoadEvent{
	
	public NewCityRoadEvent(int time, String id, String srcJun, String destJunc, int length, int co2Limit, int maxSpeed, Weather weather){
			super(time, id, srcJun, destJunc, length, co2Limit, maxSpeed, weather);
	}

	protected Road createRoad() {
		CityRoad r=null;
		if(destJ!= null && srcJ != null) {
			r= new CityRoad(id,srcJ,destJ,length,co2Limit,maxSpeed,weather);
		}
		return r;
	}
}
