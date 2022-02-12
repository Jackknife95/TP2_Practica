package simulator.model;

import java.util.List;

public class MoveAllStrategy implements DequeuingStrategy {

	MoveAllStrategy(){
		
	}
	
	//TODO comprobar que funcionaria el metodo
	
	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) {
		List<Vehicle> v= q;
		return v;
	}

}
