package simulator.model;

import java.util.LinkedList;
import java.util.List;

public class MoveAllStrategy implements DequeuingStrategy {
	
	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) {
		
		// Hago esto para que la lista v no se este referenciando a la lista q, y para preservar orden
		List<Vehicle> v = new LinkedList<Vehicle>(); 
		
		for(Vehicle vehicle : q) {
			v.add(vehicle);
		}
		return v;
	}

}
