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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import simulator.model.RoadMap;
import simulator.model.Vehicle;

public class ChangeCO2Dialog extends JDialog {

	private int status;	
	private JComboBox<Vehicle> vehicles;
	private JComboBox<Integer> contClass;
	private JSpinner ticks;
	
	public ChangeCO2Dialog (){
		
		this.status = 0;
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setPreferredSize(new Dimension(400, 200));
		
		JLabel help_msg = new JLabel("Schedule an event to change the CO2 class of a vehicle");
		JLabel help_msg2 = new JLabel(" after a given number of simulations ticks from now.");
		help_msg.setAlignmentX(CENTER_ALIGNMENT);
		help_msg2.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(help_msg);
		mainPanel.add(help_msg2);
			
		JPanel panel2 = new JPanel();
		vehicles = new JComboBox<Vehicle>(new DefaultComboBoxModel<Vehicle>());
		contClass = new JComboBox<Integer>(new DefaultComboBoxModel<Integer>());	
		ticks = new JSpinner(new  SpinnerNumberModel(1, 1, 100, 1));		
		
		// Añadimos los números de la clase de contaminación
		for(int i = 0; i < 10; i++) {
			contClass.addItem(new Integer(i+1));
		}
		
		mainPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Crea espacio en altura
		panel2.add(new JLabel("Vehicle: "));
		panel2.add(vehicles);
		panel2.add(Box.createHorizontalStrut(10));	// Añade separación entre un JComboBox de otro
		panel2.add(new JLabel("CO2 Class: "));
		panel2.add(contClass);
		panel2.add(Box.createHorizontalStrut(10));
		panel2.add(new JLabel("Ticks: "));
		panel2.add(ticks);		
		mainPanel.add(panel2);	
		
		// Panel de botones 
		JPanel ButtonPanel = new JPanel();
		JButton ok = new JButton("OK");
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(vehicles.getSelectedItem() != null) {
					setStatus(1);
					setVisible(false);		
				}
			}			
		});
		
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setStatus(0);
				setVisible(false);
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
		
		for(Vehicle v : roadMap.getVehicles()) {
			vehicles.addItem(v);
		}
		setVisible(true);		
		return this.status;
	}
	
	// Setter Methods
	private void setStatus(int n) {
		this.status = n;
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
