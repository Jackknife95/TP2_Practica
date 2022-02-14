package simulator.model;

public class NewInterCityRoadEvent extends Event {

	private String id;
	private String srcJun;
	private String destJunc;
	private int length; 
	private int co2Limit; 
	private int maxSpeed; 
	private Weather weather;
	
	public NewInterCityRoadEvent(int time, String id, String srcJun, String destJunc, int length, int co2Limit, int maxSpeed, Weather weather) {
			
			super(time);
				
			this.id=id;
			this.srcJun=srcJun;
			this.destJunc=destJunc;
			this.length=length;
			this.co2Limit= co2Limit;
			this.maxSpeed= maxSpeed;
			this.weather= weather;
	}

	@Override
	void execute(RoadMap map) {
		
		Junction dJ = map.getJuction(destJunc);
		
		Junction sJ = map.getJuction(srcJun);
		
		InterCityRoad road= null;
		
		if(dJ!= null && sJ != null) {
			road= new InterCityRoad(id,sJ,dJ,length,co2Limit,maxSpeed,weather);
			if(map.getRoad(road.getId())==null) {
				map.addRoad(road);
			}

		}
	}
}
