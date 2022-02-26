package simulator.factories;

import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;

public class SetWeatherEventBuilder extends Builder<Event> {

	public SetWeatherEventBuilder() {
		super("set_weather");
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		
		JSONArray ja = data.getJSONArray("info");
		
		LinkedList<Pair<String,Weather>> l = new LinkedList<Pair<String,Weather>>();
		
		for (int i=0; i<ja.length(); i++) {
			JSONObject jo =(JSONObject) ja.get(i);
			
			Weather we= Weather.valueOf(jo.getString("weather"));
			
			Pair<String,Weather> p = new Pair<String,Weather>(jo.getString("road"),we);
			//TODO REVISAR LA LINEA ANTERIOR
			
			l.add(p);
		}
		
		
		
		SetWeatherEvent e =new SetWeatherEvent(data.getInt("time"), l);
		
		return e;
	}
	
}
