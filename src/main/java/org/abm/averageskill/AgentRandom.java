package org.abm.averageskill;

import java.util.Date;

import cern.jet.random.Normal;
import cern.jet.random.engine.MersenneTwister;

public class AgentRandom extends Agent {
	Normal normal = new Normal(1d, .2d, new MersenneTwister(new Date()));

	@Override
	public void doSomeWork() {
		workOrder.complete(normal.nextDouble());
	}
}
