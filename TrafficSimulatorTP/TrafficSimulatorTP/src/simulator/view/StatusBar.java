package simulator.view;

import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class StatusBar extends JPanel implements TrafficSimObserver {
	
	private static final long serialVersionUID = 1L;
	private Controller _ctrl;
	private int time;
	private String eventMessage;
	
	private JLabel tiempo_sim;
	private JLabel event_msg;
	
	StatusBar(Controller controller){
		this._ctrl = controller;
		_ctrl.addObserver(this);
		initGUI();
	}
	
	
	private void initGUI() {
		this.time = 0;
		this.eventMessage = "";
		
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBorder(BorderFactory.createLoweredBevelBorder());
		
		tiempo_sim = new JLabel("Time: " + time);
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setVisible(true);
		event_msg = new JLabel(eventMessage);
		
		this.add(tiempo_sim);
		this.add(Box.createHorizontalStrut(150));
		this.add(separator);
		this.add(event_msg);	
	}
	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		this.time = time;
		this.eventMessage = "";	
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		this.time = time;
		this.eventMessage = "";		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		this.time = time;
		this.eventMessage = "Event added " + "(" + e.toString() + ")";	
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		this.time = 0;
		this.eventMessage = "";		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// Do nothing
		
	}

	@Override
	public void onError(String msg) {
		// Do nothing
		
	}

}
