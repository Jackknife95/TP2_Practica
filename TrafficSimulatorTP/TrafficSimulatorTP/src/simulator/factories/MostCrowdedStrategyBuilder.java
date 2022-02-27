package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.MostCrowdedStrategy;

public class MostCrowdedStrategyBuilder extends Builder<LightSwitchingStrategy>{

	public MostCrowdedStrategyBuilder() {
		super("most_crowded_lss");
	}

	@Override
	protected LightSwitchingStrategy createTheInstance(JSONObject data) {
		
		MostCrowdedStrategy mcs = null;
		
		if(data != null) {
			if(data.isEmpty()) {
				mcs = new MostCrowdedStrategy(1);
			}
			else {
				mcs = new MostCrowdedStrategy(data.getInt("timeslot"));
			}
		}
		else {
			mcs = new MostCrowdedStrategy(1);
		}
		
		return mcs;
	}

}
