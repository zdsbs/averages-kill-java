package org.abm.averageskill;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AgentTest {
	@Test
	public void having_an_agent_do_some_work_on_an_order_actually_does_some_work_on_that_order() throws Exception {
		WorkOrder workOrder = new WorkOrder(3);
		Agent agent = new Agent();
		agent.getA(workOrder);
		agent.doSomeWork();
		assertFalse(workOrder.workUnitsAreComplete());
		agent.doSomeWork();
		assertFalse(workOrder.workUnitsAreComplete());
		agent.doSomeWork();
		assertTrue(workOrder.workUnitsAreComplete());
	}

	@Test
	public void hasWork_works() throws Exception {
		Agent agent = new Agent();
		assertFalse(Agent.hasWork().f(agent));
		agent.getA(new WorkOrder(3));
		assertTrue(Agent.hasWork().f(agent));
	}

	@Test
	public void hasNoWork_works() throws Exception {
		Agent agent = new Agent();
		assertTrue(Agent.hasNoWork().f(agent));
		agent.getA(new WorkOrder(3));
		assertFalse(Agent.hasNoWork().f(agent));
	}
}
