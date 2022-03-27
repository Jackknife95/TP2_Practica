package simulator.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.Vehicle;
import simulator.model.Weather;

public class ChangeWeatherDialog extends JDialog {

	private int state;
	private JComboBox<Road> roads;
	private JComboBox<Weather> weather;
	private JSpinner ticks;
	
	public ChangeWeatherDialog() {
		
		this.state = 0;
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(new JLabel("Schedule an event to change the weather of a road"
				+ " after a given number of simulations ticks from now."));
		
		JPanel panel2 = new JPanel();
		roads = new JComboBox<Road>(new DefaultComboBoxModel<>());
		weather = new JComboBox<Weather>(new DefaultComboBoxModel<>(Weather.values()));
		ticks = new JSpinner(new  SpinnerNumberModel(1, 1, 100, 1));
		
		panel2.add(roads);
		panel2.add(weather);
		panel2.add(ticks);
		mainPanel.add(panel2);
		
		// Panel de Botones
		JPanel ButtonPanel = new JPanel();
		JButton ok = new JButton("OK");
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(roads.getSelectedItem() != null) {
					setState(1);
					setVisible(false);				
				}
			}			
		});
		
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setState(0);
				setVisible(false);			
			}		
		});	
		
		ButtonPanel.add(cancel);
		ButtonPanel.add(ok);		
		mainPanel.add(ButtonPanel);	
		this.add(mainPanel);
	}
	
	public int open(RoadMap roadMap) {
		
		for(Road r : roadMap.getRoads()) {
			roads.addItem(r);
		}
		setVisible(true);		
		return this.state;
	}
	
	// Setter Method
	private void setState(int n) {
		this.state = n;
	}
	
	// Getter Methods
	public int getTicks() {
		return (Integer)ticks.getValue();
	}
	
	public Road getRoad() {
		return (Road)roads.getSelectedItem();
	}
	
	public Weather getWeather() {
		return (Weather)weather.getSelectedItem();
	}
	
}
