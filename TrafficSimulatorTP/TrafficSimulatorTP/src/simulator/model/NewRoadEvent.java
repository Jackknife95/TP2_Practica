package simulator.model;

public abstract class NewRoadEvent extends Event {
	protected String id;
	protected String srcJun;
	protected String destJunc;
	protected int length; 
	protected int co2Limit; 
	protected int maxSpeed; 
	protected Weather weather;
	NewRoadEvent(int time, String id, String srcJun, String destJunc, int length, int co2Limit, int maxSpeed, Weather weather) {
		super(time);
		// TODO Auto-generated constructor stub
		this.id=id;
		this.srcJun=srcJun;
		this.destJunc=destJunc;
		this.length=length;
		this.co2Limit= co2Limit;
		this.maxSpeed= maxSpeed;
		this.weather= weather;
	}
	@Override
	abstract void execute(RoadMap map);
	
}
