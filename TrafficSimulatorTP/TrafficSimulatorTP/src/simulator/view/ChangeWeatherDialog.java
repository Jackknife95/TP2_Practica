package simulator.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.Weather;

public class ChangeWeatherDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private int status;
	private JComboBox<Road> roads;
	private DefaultComboBoxModel<Road> roadsModel;
	private JComboBox<Weather> weather;
	private DefaultComboBoxModel<Weather> weatherModel;
	private JSpinner ticks;
	
	public ChangeWeatherDialog(JFrame frame) {
		super(frame, true);
		this.status = 0;
		JPanel mainPanel = new JPanel(); 
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setPreferredSize(new Dimension(400, 200));
		
		JLabel help_msg = new JLabel("Schedule an event to change the weather of a road");
		JLabel help_msg2 = new JLabel(" after a given number of simulations ticks from now.");
		help_msg.setAlignmentX(CENTER_ALIGNMENT);
		help_msg2.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(help_msg);
		mainPanel.add(help_msg2);
		
		JPanel panel2 = new JPanel();
		roadsModel = new DefaultComboBoxModel<Road>();
		weatherModel = new DefaultComboBoxModel<Weather>(Weather.values());
		
		roads = new JComboBox<Road>(roadsModel);	
		weather = new JComboBox<Weather>(weatherModel);	
		ticks = new JSpinner(new  SpinnerNumberModel(1, 1, 100, 1));
			
		mainPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Crea espacio en altura
		panel2.add(new JLabel("Road: "));
		panel2.add(roads);
		panel2.add(Box.createHorizontalStrut(10));	// Añade separación entre un JComboBox de otro
		panel2.add(new JLabel("Weather: "));
		panel2.add(weather);
		panel2.add(Box.createHorizontalStrut(10));	// Añade separación entre un JComboBox de otro
		panel2.add(new JLabel("Ticks: "));
		panel2.add(ticks);
		mainPanel.add(panel2);
		
		// Panel de Botones
		JPanel ButtonPanel = new JPanel();
		JButton ok = new JButton("OK");
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(roadsModel.getSelectedItem() != null) {
					status = 1;
					ChangeWeatherDialog.this.setVisible(false);				
				}
			}			
		});
		
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				status = 0;
				ChangeWeatherDialog.this.setVisible(false);			
			}		
		});	
		
		ButtonPanel.add(cancel);
		ButtonPanel.add(Box.createHorizontalStrut(10));
		ButtonPanel.add(ok);		
		mainPanel.add(ButtonPanel);	
		this.add(mainPanel);
		pack();
		setResizable(false);
	}
	
	public int open(RoadMap roadMap) {
		
		roadsModel.removeAllElements();
		for(Road r : roadMap.getRoads()) {
			roadsModel.addElement(r);
		}
		setVisible(true);		
		return this.status;
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
