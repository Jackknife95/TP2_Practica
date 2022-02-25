package simulator.factories;

import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.NewJunctionEvent;

public class NewJunctionEventBuilder extends Builder<Event> {
	
	private Factory<LightSwitchingStrategy> lssFactory;
	private Factory<DequeuingStrategy> dqsFactory;
	
	public NewJunctionEventBuilder(Factory<LightSwitchingStrategy> lssFactory, Factory<DequeuingStrategy> dqsFactory) {
		
		super("new_junction");
		this.lssFactory= lssFactory;
		this.dqsFactory= dqsFactory;
		
	}
	

	@Override
	protected Event createTheInstance(JSONObject data) {
		
		LightSwitchingStrategy l =lssFactory.createInstance(data.getJSONObject("ls_strategy"));
		
		DequeuingStrategy d =dqsFactory.createInstance(data.getJSONObject("dq_strategy"));
		
		NewJunctionEvent e = new NewJunctionEvent(data.getInt("time"),data.getString("id"),l,d,data.getJSONArray("coor").getInt(0),data.getJSONArray("coor").getInt(1));
		
		return e;
	}

}