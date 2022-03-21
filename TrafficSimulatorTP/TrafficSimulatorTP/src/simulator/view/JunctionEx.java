package simulator.view;

public class JunctionEx {
	private String _id,_green,_queues;

	public JunctionEx(String id , String green,String queues) {	
		_id=id;
		_green=green;
		_queues= queues;
	}

	public String get_id() {
		return _id;
	}

	public String get_green() {
		return _green;
	}

	public String get_queues() {
		return _queues;
	}

	

}