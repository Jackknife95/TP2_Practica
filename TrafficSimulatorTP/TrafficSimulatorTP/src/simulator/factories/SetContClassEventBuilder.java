package simulator.factories;

import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.NewSetContClassEvent;

public class SetContClassEventBuilder extends Builder<Event>{

	public SetContClassEventBuilder() {
		super("set_cont_class");
	}
	

	@Override
	protected Event createTheInstance(JSONObject data) {
		
		JSONArray ja = data.getJSONArray("info");
		
		LinkedList<Pair<String,Integer>> l = new LinkedList<Pair<String,Integer>>();
		
		for (int i=0; i<ja.length(); i++) {
			JSONObject jo =(JSONObject) ja.get(i);
			
			Pair<String,Integer> p = new Pair<String,Integer>(jo.getString("vehicle"),jo.getInt("class"));
			
			l.add(p);
		}
		
		
		NewSetContClassEvent e = new NewSetContClassEvent(data.getInt("time"), l);
		
		return e;
	}
}
