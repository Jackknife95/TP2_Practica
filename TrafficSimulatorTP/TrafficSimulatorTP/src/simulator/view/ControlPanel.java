package simulator.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.NewSetContClassEvent;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;

public class ControlPanel extends JPanel implements TrafficSimObserver {

	private Controller _ctrl;
	private RoadMap roadMap;
	private int time;
	
	// GUI
	private JToolBar toolbar;
	private JButton loadEventButton;
	private JButton switchContClassButton;
	private JFileChooser fileChooser;
	
	public ControlPanel(Controller controller) {
		this._ctrl = controller;
		_ctrl.addObserver(this);
	}
	
	private void initGUI() {
		toolbar = new JToolBar();
		this.add(toolbar, BorderLayout.PAGE_START);
			
		loadEventButton = new JButton();
		loadEventButton.setToolTipText("Abrir Fichero");	// Mensaje que aparece cuando se sitúa el cursor encima del botón	
		loadEventButton.setIcon(new ImageIcon("resources/icons/open.png"));	 // Establece un icono para el botón
		loadEventButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				loadFile();	
			}
		});	
		toolbar.add(loadEventButton);	// Añade el botón al toolbar
		
		switchContClassButton = new JButton();
		switchContClassButton.setToolTipText("Cambiar Clase de Contaminación");
		switchContClassButton.setIcon(new ImageIcon("resources/icons/co2class.png"));
		switchContClassButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				changeCO2Class();			
			}		
		});
		toolbar.add(switchContClassButton);	// Añade el botón al toolbar
		
		
	}
	
	private void loadFile()  {
		fileChooser = new JFileChooser();
		int ret = fileChooser.showOpenDialog(null);
		
		if(ret == JFileChooser.APPROVE_OPTION) {
			try {
				_ctrl.reset();
				File file = fileChooser.getSelectedFile();
				_ctrl.loadEvents(new FileInputStream(file));
				
			}
			catch(IllegalArgumentException i) {
				JOptionPane.showMessageDialog(null, "Error al cargar el fichero de Eventos", "Error", JOptionPane.WARNING_MESSAGE);
				
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null, "El fichero seleccionado no existe!", "Error", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	
	private void changeCO2Class() {
		
		ChangeCO2Dialog dialog = new ChangeCO2Dialog();
		int ret = dialog.open(roadMap);
		
		List<Pair<String, Integer>> p = new ArrayList<Pair<String,Integer>>();
		
		if(ret == 1) {
			p.add(new Pair<String, Integer>(dialog.getVehicle().getId(), dialog.getCO2Class()));
			
			try {
				_ctrl.addEvent(new NewSetContClassEvent(time + dialog.getTicks(), p));			
			}
			catch(IllegalArgumentException i) {
				throw new IllegalArgumentException(i.getMessage());
			}
			
		}	
	}
	
	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// Do nothing
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// Do nothing
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(String msg) {
		// Do nothing
		
	}

}
