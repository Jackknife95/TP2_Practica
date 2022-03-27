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

import simulator.model.RoadMap;
import simulator.model.Vehicle;

public class ChangeCO2Dialog extends JDialog {

	private int state;	
	private JComboBox<Vehicle> vehicles;
	private JComboBox<Integer> contClass;
	private JSpinner ticks;
	
	public ChangeCO2Dialog (){
		
		this.state = 0;
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(new JLabel("Schedule an event to change the CO2 class of a vehicle"
				+ " after a given number of simulations ticks from now."));
		
		
		JPanel panel2 = new JPanel();
		vehicles = new JComboBox<Vehicle>(new DefaultComboBoxModel<Vehicle>());
		contClass = new JComboBox<Integer>(new DefaultComboBoxModel<Integer>());	
		ticks = new JSpinner(new  SpinnerNumberModel(1, 1, 100, 1));		
		
		// Añadimos los números de la clase de contaminación
		for(int i = 0; i < 10; i++) {
			contClass.addItem(new Integer(i+1));
		}
		
		panel2.add(vehicles);
		panel2.add(contClass);
		panel2.add(ticks);		
		mainPanel.add(panel2);	
		
		// Panel de botones 
		JPanel ButtonPanel = new JPanel();
		JButton ok = new JButton("OK");
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(vehicles.getSelectedItem() != null) {
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
		
		for(Vehicle v : roadMap.getVehicles()) {
			vehicles.addItem(v);
		}
		setVisible(true);		
		return this.state;
	}
	
	// Setter Methods
	private void setState(int n) {
		this.state = n;
	}
	
	// Getter Methods
	public int getTicks() {
		return (Integer)ticks.getValue();
	}
	
	public Vehicle getVehicle() {
		return (Vehicle)vehicles.getSelectedItem();
	}
	
	public int getCO2Class() {
		return (Integer)contClass.getSelectedItem();
	}
	
}
