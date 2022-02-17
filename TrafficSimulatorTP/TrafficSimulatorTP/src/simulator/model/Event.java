package simulator.model;

public abstract class Event implements Comparable<Event> {

	protected int _time;

	Event(int time) {
		if (time < 1)
			throw new IllegalArgumentException("Time must be positive (" + time + ")");
		else
			_time = time;
	}

	int getTime() {
		return _time;
	}

	@Override
	public int compareTo(Event o) {
		
		int i= 2; // iniciamos a un valor que no corresponda a nada 
		
		if(this._time<o._time) {
			i= -1;						// Evento planeado en el futuro
		}
		
		else if(this._time>o._time) {
			i=1;						// Evento hecho en el pasado
		}
		
		else {
			i= 0;						//hora de hace el evento
		}
		
		return i;
	}

	abstract void execute(RoadMap map);
}
