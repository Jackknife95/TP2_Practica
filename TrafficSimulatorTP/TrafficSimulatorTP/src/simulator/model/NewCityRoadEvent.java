package simulator.model;

public class NewCityRoadEvent extends NewRoadEvent{
	
	private String id;
	private String srcJun;
	private String destJunc;
	private int length; 
	private int co2Limit; 
	private int maxSpeed; 
	private Weather weather;
	
	
	public NewCityRoadEvent(int time, String id, String srcJun, String destJunc, int length, int co2Limit, int maxSpeed, Weather weather){
				super(time,id,srcJun,destJunc, length, co2Limit, maxSpeed, weather);
			}

	@Override
	void execute(RoadMap map) {
		
		Junction dJ = map.getJuction(destJunc);
		
		Junction sJ = map.getJuction(srcJun);
		
		CityRoad road= null;
		
		if(dJ!= null && sJ != null) {
			road= new CityRoad(id,sJ,dJ,length,co2Limit,maxSpeed,weather);
			if(map.getRoad(road.getId())==null) {
				map.addRoad(road);
			}
		}
		
		
	} 
}
