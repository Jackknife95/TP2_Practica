package simulator.model;

public abstract class NewRoadEvent extends Event {
	
	protected String id;
	protected String srcJun;
	protected String destJunc;
	protected int length; 
	protected int co2Limit; 
	protected int maxSpeed; 
	protected Weather weather;
	protected Junction srcJ , destJ;
	NewRoadEvent(int time, String id, String srcJun, String destJunc, int length, int co2Limit, int maxSpeed, Weather weather) {
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
		destJ = map.getJunction(destJunc);
		
		srcJ = map.getJunction(srcJun);
		
		Road r =createRoad();
		
		if(r!=null) {
			map.addRoad(r);
		}
		else {
			throw new IllegalArgumentException("Invalid Road");
		}
	}
	
	abstract protected Road createRoad();
}
