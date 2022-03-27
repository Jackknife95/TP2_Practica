package simulator.view;

import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class StatusBar extends JPanel implements TrafficSimObserver {
	
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
		
		this.setLayout(new FlowLayout());
		this.setBorder(BorderFactory.createLoweredBevelBorder());
		
		tiempo_sim = new JLabel("Time: " + time, JLabel.LEFT);
		event_msg = new JLabel("Event added " + "(" + eventMessage + ")", JLabel.CENTER);
		
		this.add(tiempo_sim);
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
		this.eventMessage = e.toString();	
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
