package simulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.VehicleStatus;
import simulator.model.Weather;

public class MapByRoadComponent extends JPanel implements TrafficSimObserver {

	private static final long serialVersionUID = 1L;
	private static final int _JRADIUS = 10;
	private static final Color _BG_COLOR = Color.WHITE;
	private static final Color _ROAD_COLOR = Color.BLACK;
	private static final Color _ROAD_LABEL_COLOR = Color.black;
	private static final Color _ORG_ROAD_COLOR = Color.BLUE;
	private static final Color _JUNCTION_LABEL_COLOR = new Color(200, 100, 0);
	private static final Color _GREEN_LIGHT_COLOR = Color.GREEN;
	private static final Color _RED_LIGHT_COLOR = Color.RED;
	
	private RoadMap _map;
	private Image _car;
	private ArrayList<Image> _contImage;
	
	MapByRoadComponent(Controller ctrl){
		ctrl.addObserver(this);
		initGUI();
	}
	
	private void initGUI() {
		_contImage = new ArrayList<Image>(6);
		
		// Carga las Imágenes
		_car = loadImage("car.png");
		
		for(int i = 0; i < 6; i++) {
			_contImage.add(loadImage("cont_" + i + ".png"));
		}		
		setPreferredSize(new Dimension(300, 200));
	}
	
	// loads an image from a file
	private Image loadImage(String img) {
		Image i = null;
		try {
			return ImageIO.read(new File("resources/icons/" + img));
		} catch (IOException e) {
		}
		return i;
	}
	
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		// clear with a background color
		g.setColor(_BG_COLOR);
		g.clearRect(0, 0, getWidth(), getHeight());

		if (_map == null || _map.getJunctions().size() == 0) {
			g.setColor(Color.red);
			g.drawString("No map yet!", getWidth() / 2 - 50, getHeight() / 2);
		} else {
			drawMap(g);
		}
	}
	
	private void drawMap(Graphics g) {
		int y = 50;
		int x1 = 50;
		int x2 = getWidth() - 100;
		
		for(Road r : _map.getRoads()) {
			// Dibuja la etiqueta y la carretera
			g.setColor(_ROAD_LABEL_COLOR);
			g.drawString(r.getId(), x1 - 35, y);
			g.setColor(_ROAD_COLOR);
			g.drawLine(x1, y, x2, y);
					
			int index = r.getDest().getGreenLightIndex();
			Color cruce_origen = _ORG_ROAD_COLOR;
			Color cruce_destino = _RED_LIGHT_COLOR;
			
			if(index != -1 && r.equals(r.getDest().getInRoads().get(index))){
				cruce_destino = _GREEN_LIGHT_COLOR;
			}
			
			// Dibuja los cruces
			g.setColor(cruce_origen);
			g.fillOval(x1, y-5, _JRADIUS, _JRADIUS);
			g.setColor(cruce_destino);
			g.fillOval(x2, y-5, _JRADIUS, _JRADIUS);
			
			// Dibuja las etiquetas de los Cruces
			g.setColor(_JUNCTION_LABEL_COLOR);
			g.drawString(r.getSrc().getId(), x1, y-6);
			g.drawString(r.getDest().getId(), x2, y-6);
			
			// Dibuja el Vehiculo
			drawVehicles(g, x1, x2, y, r.getId());
			
			// Dibuja el Icono de las condiciones atmosfericas		
			Weather weather = r.getWeather();			
			if(weather == Weather.SUNNY) {
				g.drawImage(loadImage("sun.png"), x2+12, y-18, 32, 32, this);
			}
			else if(weather == Weather.CLOUDY) {
				g.drawImage(loadImage("cloud.png"), x2+12, y-18, 32, 32, this);
			}
			else if(weather == Weather.RAINY) {
				g.drawImage(loadImage("rain.png"), x2+12, y-18, 32, 32, this);
			}
			else if(weather == Weather.WINDY) {
				g.drawImage(loadImage("wind.png"), x2+12, y-18, 32, 32, this);
			}
			else {
				g.drawImage(loadImage("storm.png"), x2+12, y-18, 32, 32, this);
			}
							
			// Dibuja el Icono de los niveles de contaminacion
			int A = r.getTotalCO2();
			int B = r.getContLimit();
			int C = (int) Math.floor(Math.min((double) A/(1.0 + (double) B),1.0) / 0.19);
			System.out.println(C);
			g.drawImage(_contImage.get(C), x2+45, y-18, 32, 32, this);
			y += 50;
		}
	}
	
	private void drawVehicles(Graphics g, int x1, int x2, int y, String road) {
		for (Vehicle v : _map.getRoad(road).getVehicles()) {
			if (v.getStatus() != VehicleStatus.ARRIVED) {
				
				int A = v.getLocation();
				int B = v.getRoad().getLength();
				int x = x1 + (int) ((x2 - x1) * ((double) A / (double) B)); // Coordenado X del vehiculo en la carretera
				
				// Choose a color for the vehcile's label and background, depending on its
				// contamination class
				int vLabelColor = (int) (25.0 * (10.0 - (double) v.getContClass()));
				g.setColor(new Color(0, vLabelColor, 0));

				// draw an image of a car and it identifier
				g.drawImage(_car, x, y - 10, 16, 16, this);
				g.drawString(v.getId(), x, y - 10);
			}
		}
	}
	
	public void update(RoadMap map) {
		_map = map;
		repaint();
	}
	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// Do nothing
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		update(map);		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		update(map);
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		update(map);
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		update(map);
	}

	@Override
	public void onError(String msg) {
		// Do Nothing
	}

}
