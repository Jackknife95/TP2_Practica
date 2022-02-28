package simulator.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MoveFirstStrategy implements DequeuingStrategy {
	
	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) {
		
		List<Vehicle> v = new ArrayList<Vehicle>(1);
		
		if(q.size() > 0) {
			
			v.add(q.get(0));
		}
			
		return v;
		
	}
}
