package simulator.factories;

import simulator.model.Event;
import simulator.model.NewInterCityRoadEvent;

public class NewInterCityRoadEventBuilder extends NewRoadEventBuilder{

	NewInterCityRoadEventBuilder() {
		super("new_inter_city_road");
	}

	@Override
	protected Event createTheEvent() {
		NewInterCityRoadEvent e = new NewInterCityRoadEvent(time,id,src,dest,length,co2Limit, maxSpeed,weather);
		return e;
	}

}