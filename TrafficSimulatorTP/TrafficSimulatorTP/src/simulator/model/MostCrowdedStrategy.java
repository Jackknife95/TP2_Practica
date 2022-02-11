package simulator.model;

import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy {
	 private int timeSlot;
	MostCrowdedStrategy(int timeSlot){
		this.timeSlot= timeSlot;
	}

	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime,
		int currTime) {
		int result=-1;
		
		if (currTime-lastSwitchingTime <timeSlot) {
			result= currGreen;
		}
		else {
			result =-1;
			
			for(List<Vehicle> lista: qs ) {
				if(lista.size()>result) {
					result= qs.indexOf(lista);
				}
			}
			
		}
		
		/*------------------------------- TODO FALTA TERMINARLO------------------*/
		return result;
	}

}