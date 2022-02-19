package simulator.model;

public class NewCityRoadEvent extends NewRoadEvent{
	
	public NewCityRoadEvent(int time, String id, String srcJun, String destJunc, int length, int co2Limit, int maxSpeed, Weather weather){
			super(time, id, srcJun, destJunc, length, co2Limit, maxSpeed, weather);
	}

	@Override
	void execute(RoadMap map) {
		
		Junction dJ = map.getJunction(destJunc);
		
		Junction sJ = map.getJunction(srcJun);
				
		if(dJ!= null && sJ != null) {
			CityRoad road = new CityRoad(id,sJ,dJ,length,co2Limit,maxSpeed,weather);
			map.addRoad(road);
		}
	} 
	
}
