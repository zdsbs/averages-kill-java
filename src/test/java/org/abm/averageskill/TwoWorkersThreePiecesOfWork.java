package org.abm.averageskill;

import static org.abm.averageskill.ConfigBuilder.worker;
import static org.junit.Assert.assertEquals;

import org.abm.averageskill.simulation.Config;
import org.abm.averageskill.simulation.Results;
import org.abm.averageskill.simulation.Simulation;
import org.junit.Ignore;
import org.junit.Test;

public class TwoWorkersThreePiecesOfWork {
	// 0: 2 a[-,] b[,]
	// 10: 2 a[,-] b[,]
	// 15: 1 a[-,] b[-,]
	// 25: 1 a[,-] b[,-] 1
	// 30: a[-,] b[-,] 1
	// 40: a[,-] b[,-] 2
	// 45: a[] b[-,] 2
	// 55: a[] b[,-] 2
	// 60: a[] b[] 3
	@Ignore
	@Test
	public void transition_time_5_completion_time_10() throws Exception {
		Config config = ConfigBuilder.workOrders(3).workers(worker(10, 5), worker(10, 5)).build();
		Results results = new Simulation().run(config);
		assertEquals(3, results.numberOfWorkOrdersCompleted);
		assertEquals(60f, results.simulationTimeRun, 0f);
	}
	
	// 0 : 2 a[-,] b[,]
	// 10: 2 a[,-] b[,]
	// 11: 1 a[-,] b[-,]
	// 21: 1 a[,-] b[,-]
	// 22: a[-,] b[-,] 1
	// 32: a[,-] b[,-] 1
	// 33: a[] b[-,] 2
	// 43: a[] b[,-] 2
	// 44: a[] b[] 3
	
	@Ignore
	@Test
	public void transition_time_1_completion_time_10() throws Exception {
		Config config = ConfigBuilder.workOrders(3).workers(worker(10, 1), worker(10, 1)).build();
		Results results = new Simulation().run(config);
		assertEquals(3, results.numberOfWorkOrdersCompleted);
		assertEquals(44f, results.simulationTimeRun, 0f);
	}
	
}
