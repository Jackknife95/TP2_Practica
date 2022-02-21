package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.RoundRobinStrategy;

public class RoundRobinStrategyBuilder extends Builder<LightSwitchingStrategy>{

	RoundRobinStrategyBuilder() {
		super("round_robin_lss");
	}

	@Override
	protected LightSwitchingStrategy createTheInstance(JSONObject data) {
		 
		RoundRobinStrategy rrs= null;
		if(data.isEmpty()) {
			rrs= new RoundRobinStrategy(1);
		}
		else {
			rrs= new RoundRobinStrategy(data.getInt("timeslot"));
		}
		return rrs;
	}

}
