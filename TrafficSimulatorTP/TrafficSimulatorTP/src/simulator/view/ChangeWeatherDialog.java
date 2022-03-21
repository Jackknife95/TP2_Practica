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
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(new JLabel("Selecciona una Carretera y una Condición Atmosférica"));
		
		
		JPanel panel2 = new JPanel();
		roads = new JComboBox<Road>();
		weather = new JComboBox<Weather>();
		ticks = new JSpinner();
		roads.setModel(new DefaultComboBoxModel());
		weather.setModel(new DefaultComboBoxModel());
		ticks.setModel((SpinnerModel) new DefaultComboBoxModel());
		
		panel2.add(roads);
		panel2.add(weather);
		panel2.add(ticks);
		mainPanel.add(panel2);
		
		JPanel ButtonPanel = new JPanel();
		JButton ok = new JButton("OK");
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(roads.getSelectedItem() != null) {
					setVisible(false);
					setState(1);
				}
			}			
		});
		
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				setState(0);
			}		
		});	
		
		ButtonPanel.add(ok);
		ButtonPanel.add(cancel);
		
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
