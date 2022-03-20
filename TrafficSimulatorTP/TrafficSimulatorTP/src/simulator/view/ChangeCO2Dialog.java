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

import simulator.model.RoadMap;
import simulator.model.Vehicle;

public class ChangeCO2Dialog extends JDialog {

	private int state;	// Queda pendiente de inicialización
	private JComboBox<Vehicle> vehicles;
	private JComboBox<Integer> contClass;
	private JSpinner ticks;
	
	public ChangeCO2Dialog (){
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(new JLabel("Selecciona un Vehículo y una Clase de Contaminación"));
		
		JPanel panel2 = new JPanel();
		vehicles = new JComboBox<Vehicle>();
		contClass = new JComboBox<Integer>();
		vehicles.setModel(new DefaultComboBoxModel());
		contClass.setModel(new DefaultComboBoxModel());	
		ticks.setModel((SpinnerModel) new DefaultComboBoxModel());
		
		panel2.add(vehicles);
		panel2.add(contClass);
		panel2.add(ticks);
		
		mainPanel.add(panel2);
		
		JPanel ButtonPanel = new JPanel();
		JButton ok = new JButton("OK");
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(vehicles.getSelectedItem() != null) {
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
