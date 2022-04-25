package simulator.view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class EventsTableModel extends AbstractTableModel implements TrafficSimObserver {

	private static final long serialVersionUID = 1L;
	private List<Event> _events;
	private String[] _colNames = { "Time", "Desc." };

	public EventsTableModel(Controller _ctrl) {
		_ctrl.addObserver(this);
	}

	public void update() {
		// observar que si no refresco la tabla no se carga
		// La tabla es la represantacio³n visual de una estructura de datos,
		// en este caso de un ArrayList, hay que notificar los cambios.
		
		// We need to notify changes, otherwise the table does not refresh.
		fireTableDataChanged();	
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	//si no pongo esto no coge el nombre de las columnas
	//
	//this is for the column header
	@Override
	public String getColumnName(int col) {
		return _colNames[col];
	}

	@Override
	// metodo obligatorio, probad a quitarlo, no compila
	//
	// this is for the number of columns
	public int getColumnCount() {
		return _colNames.length;
	}

	@Override
	// metodo obligatorio
	//
	// the number of row, like those in the events list
	public int getRowCount() {
		return _events == null ? 0 : _events.size();
	}

	@Override
	// metodo obligatorio
	// asi es como se va a cargar la tabla desde el ArrayList
	// el indice del arrayList es el numero de fila pq en este ejemplo
	// quiero enumerarlos.
	//
	// returns the value of a particular cell 
	public Object getValueAt(int rowIndex, int columnIndex) {
		String s = "";
		Event e = _events.get(rowIndex);
		switch ( columnIndex ) {
		
			case 0:
				s = "" + e.getTime();
			break;
			
			case 1:
				s = e.toString();
			break;
			
			default:
			assert(false);
		}
		return s;
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// Do Nothing	
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		_events = events;
		update();
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		_events = events;
		update();
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		_events = events;
		update();
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		_events = events;
		update();
	}

	@Override
	public void onError(String msg) {
		// Do Nothing
		
	}
}
