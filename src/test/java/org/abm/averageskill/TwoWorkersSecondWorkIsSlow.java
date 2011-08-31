package org.abm.averageskill;

import static org.abm.averageskill.ConfigBuilder.worker;
import static org.junit.Assert.assertEquals;

import org.abm.averageskill.simulation.Config;
import org.abm.averageskill.simulation.Results;
import org.abm.averageskill.simulation.Simulation;
import org.junit.Test;

public class TwoWorkersSecondWorkIsSlow {
	// 3 -> 10 A 5, 30 B 5
	
	// 0:	3A00		0B00		0	
	// 0:	2A10		0B00		0	
	// 10:	2A01		0B00		0
	// 15:	1A10		1B00		0
	// 15:	1A10		0B10		0	//B completes at 45, A completes at 25
	// 25:	1A01		0B10		0	//A transitions at 30
	// 30:	1A00		1B10		0
	// 30:	0A10		1B10		0 	//A completes at 40
	// 40:	0A01		1B10		0	//A transitions at 45
	// 45:	0A00		2B10		0	
	// 45:	0A00		2B01		0	//B transitions at 50	
	// 50:	0A00		2B00		1
	// 50:	0A00		1B10		1	//B completes at 80
	// 80:	0A00		1B01		1	//B transitions at 85
	// 85:	0A00		1B00		2
	// 85:	0A00		0B10		2	//B completes at 115
	//115:	0A00		0B01		2	//B transitions at 120
	//120:	0A00		0B00		3!
	@Test
	public void transition_time_5_completion_time_10() throws Exception {
		Config config = ConfigBuilder.workOrders(3).workers(worker(10, 5), worker(30, 5)).build();
		Results results = new Simulation().run(config);
		assertEquals(3, results.numberOfWorkOrdersCompleted);
		assertEquals(120f, results.simulationTimeRun, 0f);
	}
}
