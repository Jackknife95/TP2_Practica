package simulator.model;

public class NewCityRoadEvent extends Event{
	
	private road;
	
	public NewCityRoadEvent(int time, String id, String srcJun, String
			destJunc, int length, int co2Limit, int maxSpeed, Weather weather){
				super(time);
				road= new CityRoad( id,  srcJun, destJunc,  length,  co2Limit,  maxSpeed, weather);
			}

	@Override
	void execute(RoadMap map) {
		// TODO Auto-generated method stub
		
	} 
}
