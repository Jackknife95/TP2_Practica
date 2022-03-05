package simulator.model;

import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy {
	
	 private int timeSlot;
	 
	public MostCrowdedStrategy(int timeSlot){
		this.timeSlot = timeSlot;
	}

	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime, int currTime) {
		int ret = -1;
		
		if(roads.isEmpty()) {
			ret = -1;
		}		
		else if(currGreen == -1) {		
			int max = 0;
			for(List<Vehicle> lista : qs) {
				if(lista.size() > max) {
					max = lista.size();
					ret = qs.indexOf(lista);
				}
			}
		}
		else if(currTime - lastSwitchingTime < timeSlot) {
			ret = currGreen;
		}
		else {			
			ret = searchNextGreen(qs, (currGreen + 1) % roads.size());
		}	
		return ret;
	}
	
	private int searchNextGreen(List<List<Vehicle>> qs, int startIndex ) {	
		int max_index = startIndex;
		int max = qs.get(startIndex).size(); 
		int i = (startIndex + 1)% qs.size();
		
		while(i != startIndex) {
			if(qs.get(i).size() > max) {
				max = qs.get(i).size();
				max_index = i;
			}		
			i = (i + 1) % qs.size();
		}
		return max_index;		
	}
}