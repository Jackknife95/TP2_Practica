package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.NewSetContClassEvent;
import simulator.model.RoadMap;
import simulator.model.SetWeatherEvent;
import simulator.model.TrafficSimObserver;
import simulator.model.Weather;

public class ControlPanel extends JPanel implements TrafficSimObserver {

	private Controller _ctrl;
	private RoadMap roadMap;
	private int time;
	private boolean _stopped;
	
	// GUI
	private JToolBar _toolBar;
	private JButton _loadEventButton;
	private JButton _switchContClassButton;
	private JButton _changeWeatherButton;
	private JButton _runButton;
	private JButton _stopButton;
	private JButton _exitButton;
	private JFileChooser _fileChooser;
	private JSpinner _ticksSpinner;
	
	public ControlPanel(Controller controller) {
		this._ctrl = controller;
		_ctrl.addObserver(this);
	}
	
	private void initGUI() {
		_toolBar = new JToolBar();
		this.add(_toolBar, BorderLayout.PAGE_START);
			
		_loadEventButton = new JButton();
		_loadEventButton.setToolTipText("Abrir Fichero");	// Mensaje que aparece cuando se sitúa el cursor encima del botón	
		_loadEventButton.setIcon(loadImage("resources/icons/open.png"));	 // Establece un icono para el botón
		_loadEventButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				loadFile();	
			}
		});	
		_toolBar.add(_loadEventButton);	// Añade el botón al toolbar
		
		_switchContClassButton = new JButton();
		_switchContClassButton.setToolTipText("Cambiar Clase de Contaminación");
		_switchContClassButton.setIcon(loadImage("resources/icons/co2class.png"));
		_switchContClassButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				changeCO2Class();			
			}		
		});
		_toolBar.add(_switchContClassButton);	// Añade el botón al toolbar
		
		
		_changeWeatherButton = new JButton();
		_changeWeatherButton.setToolTipText("Cambiar clima");
		_changeWeatherButton.setIcon(loadImage("resources/icons/weather.png"));
		_changeWeatherButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				changeWeather();
			}		
		});
		
		_toolBar.add(_changeWeatherButton);
		
		_runButton = new JButton();
		_runButton.setToolTipText("Comenzar Simulación");
		_runButton.setIcon(loadImage("resources/icons/run.png"));
		_runButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				start();			
			}	
		});
		
		_toolBar.add(_runButton);
		
		_stopButton = new JButton();
		_stopButton.setToolTipText("Detener Simulación");
		_stopButton.setIcon(loadImage("resources/icons/stop.png"));
		_stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				stop_sim();		
			}		
		});
		
		_toolBar.add(_stopButton);
				
		_ticksSpinner = new JSpinner(new SpinnerNumberModel());
		_ticksSpinner.setMaximumSize(new Dimension(80, 40));
		_ticksSpinner.setMinimumSize(new Dimension(80, 40));
		_ticksSpinner.setPreferredSize(new Dimension(80, 40));
		
		_toolBar.add(_ticksSpinner);
		_toolBar.add(Box.createGlue());
		_toolBar.addSeparator();
		
		_exitButton = new JButton();
		_exitButton.setToolTipText("Salir");
		_exitButton.setIcon(loadImage("resources/icons/exit"));
		_exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showOptionDialog(null, message, title, optionType,
						messageType, icon, options, initialValue);
			}		
		});
		
	}
	
	private void loadFile()  {
		_fileChooser = new JFileChooser();
		int ret = _fileChooser.showOpenDialog(null);
		
		if(ret == JFileChooser.APPROVE_OPTION) {
			try {
				_ctrl.reset();
				File file = _fileChooser.getSelectedFile();
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
	
	
	private void changeWeather() {
		ChangeWeatherDialog dialog = new ChangeWeatherDialog();
		int ret = dialog.open(roadMap);
		
		List<Pair<String, Weather>> p = new ArrayList<Pair<String, Weather>>();
		if(ret == 1) {
			p.add(new Pair<String, Weather>(dialog.getRoad().getId(), dialog.getWeather()));
			
			try {
				_ctrl.addEvent(new SetWeatherEvent(time + dialog.getTicks(), p));			
			}
			catch(IllegalArgumentException i) {
				throw new IllegalArgumentException(i.getMessage());
			}
		}
	}
	
	
	private void start() {
		_loadEventButton.setEnabled(false);
		_switchContClassButton.setEnabled(false);
		_changeWeatherButton.setEnabled(false);
		
	}
	
	private void run_sim(int n) {
		if (n > 0 && !_stopped) {
				
			try {
				_ctrl.run(1);
			} catch (Exception e) {
				this._stopped = true;
			// TODO show error message and _stopped = true 
			} 
		
			SwingUtilities.invokeLater(new Runnable() {
				@Override
					public void run() {
					run_sim(n - 1);
				}
			});
		} 
		else {
		enableToolBar(true);
		_stopped = true;
		}
	}
	
	private void stop_sim() {
		_stopped = true;
	}
	
	private void enableToolBar(boolean b) {
		_toolBar.setEnabled(b);
	}
	
	protected ImageIcon loadImage(String path) {
		return new ImageIcon(Toolkit.getDefaultToolkit().createImage(path)); 
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
