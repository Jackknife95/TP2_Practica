package simulator.factories;

import java.util.LinkedList;


import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewVehicleEvent;

public class NewVehicleEventBuilder extends Builder<Event>{

	public NewVehicleEventBuilder() {
		super("new_vehicle");
	
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		
		JSONArray jo = data.getJSONArray("itinerary");
		
		LinkedList<String> l = new LinkedList<String>();
		
		for (int i =0; i < jo.length();i++) {
			l.add(jo.get(i).toString());
		}
		
		NewVehicleEvent v =new NewVehicleEvent(data.getInt("time"), data.getString("id"), data.getInt("maxspeed"), data.getInt("class"), l);
		
		return v;
	}
}
