package simulator.model;

import java.util.List;

public class MoveFirstStrategy implements DequeuingStrategy {
	
	MoveFirstStrategy(){
		
	}
	
	//TODO comprobar que funcionaria el metodo
	
	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) {
		List<Vehicle> v= q;
		for(int i = q.size()-1 ; i>1;i--) {
			v.remove(i);
		}
		return v;
	}

}
