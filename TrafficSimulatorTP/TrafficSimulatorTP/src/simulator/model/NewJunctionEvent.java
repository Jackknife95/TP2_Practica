package simulator.model;

public class NewJunctionEvent extends Event {
	
	private Junction j;
	
	public NewJunctionEvent(int time, String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor) {
			super(time);
			this.j =new Junction(id,  lsStrategy,  dqStrategy,  xCoor, yCoor);
	}

	@Override
	void execute(RoadMap map) {
		
		if(map.getJuction(j.getId()) == null) {
			map.addJunction(j);
		}
		else {
			//TODO posible excepcion por repeticion?
		}
	}
}