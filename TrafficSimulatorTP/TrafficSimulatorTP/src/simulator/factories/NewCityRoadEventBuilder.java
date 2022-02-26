package simulator.factories;

import simulator.model.Event;
import simulator.model.NewCityRoadEvent;

public class NewCityRoadEventBuilder extends NewRoadEventBuilder{

	public NewCityRoadEventBuilder() {
		super("new_city_road");
	}

	@Override
	protected Event createTheEvent() {
		NewCityRoadEvent e = new NewCityRoadEvent(time,id,src,dest,length,co2Limit, maxSpeed,weather);
		return e;
	}

}
