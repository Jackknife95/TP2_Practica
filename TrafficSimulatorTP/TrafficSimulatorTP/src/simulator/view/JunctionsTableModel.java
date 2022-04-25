package simulator.view;


import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class JunctionsTableModel extends AbstractTableModel implements TrafficSimObserver {

	private static final long serialVersionUID = 1L;
	private List<Junction> _junctions;
	private String[] _colNames = { "Id", "Green", "Queues"};

	public JunctionsTableModel(Controller _ctrl) {
		_ctrl.addObserver(this);
	}

	public void update() {
		// observar que si no refresco la tabla no se carga
		// La tabla es la represantación visual de una estructura de datos,
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
		return _junctions == null ? 0 : _junctions.size();
	}

	// "Id", "Green", "Queues".
	public Object getValueAt(int rowIndex, int columnIndex) {
		String s = "";
		Junction j = _junctions.get(rowIndex);
		switch ( columnIndex ) {	
			case 0:
				s = j.getId();
				break;		
			case 1:
				s = j.getGreenLightIndex() == -1 ? //
				"NONE" : //
				j.getInRoads().get(j.getGreenLightIndex()).getId();
				break;
			case 2:
				for (Road r : j.getInRoads()) {
				s = s + " " + r.getId() + ":" + j.getQueue(r);
				}
				break;
			default:
			assert(false);
		}
		return s;
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		_junctions = map.getJunctions();
		update();
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {

		_junctions = map.getJunctions();
		update();
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// Do Nothing
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		_junctions = map.getJunctions();
		update();
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		_junctions = map.getJunctions();
		update();
	}

	@Override
	public void onError(String msg) {
		// Do Nothing	
	}
}
