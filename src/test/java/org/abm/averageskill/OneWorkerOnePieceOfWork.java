package org.abm.averageskill;

import static org.junit.Assert.assertEquals;

import org.abm.averageskill.simulation.Config;
import org.abm.averageskill.simulation.Results;
import org.abm.averageskill.simulation.Simulation;
import org.junit.Test;


public class OneWorkerOnePieceOfWork {

	@Test
	public void transition_time_0_completion_time_10() throws Exception {
		Config config = ConfigBuilder.workOrders(1).workers(1).transitionTime(0).completionTime(10).build();
		Results results = new Simulation().run(config);
		assertEquals(1, results.numberOfWorkOrdersCompleted);
		assertEquals(10f, results.simulationTimeRun,0f);
	}
	
	@Test
	public void transition_time_1_completion_time_10() throws Exception {
		Config config = ConfigBuilder.workOrders(1).workers(1).transitionTime(1).completionTime(10).build();
		Results results = new Simulation().run(config);
		assertEquals(1, results.numberOfWorkOrdersCompleted);
		assertEquals(11f, results.simulationTimeRun,0f);
	}
	
	@Test
	public void transition_time_12_completion_time_100() throws Exception {
		Config config = ConfigBuilder.workOrders(1).workers(1).transitionTime(12).completionTime(100).build();
		Results results = new Simulation().run(config);
		assertEquals(1, results.numberOfWorkOrdersCompleted);
		assertEquals(112f, results.simulationTimeRun,0f);
	}
}
