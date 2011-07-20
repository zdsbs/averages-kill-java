package org.abm.averageskill;

import static org.junit.Assert.assertEquals;

import org.abm.averageskill.simulation.Config;
import org.abm.averageskill.simulation.Results;
import org.abm.averageskill.simulation.Simulation;
import org.junit.Test;

public class TwoWorkersTwoPiecesOfWork {
	@Test
	public void transition_time_5_completion_time_10() throws Exception {
		Config config = ConfigBuilder.workOrders(2).workers(2).transitionTime(5).completionTime(10).build();
		Results results = new Simulation().run(config);
		assertEquals(2, results.numberOfWorkOrdersCompleted);
		assertEquals(45f, results.simulationTimeRun, 0f);
	}

}
