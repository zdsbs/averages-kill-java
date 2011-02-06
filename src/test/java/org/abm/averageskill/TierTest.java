package org.abm.averageskill;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayDeque;
import java.util.Deque;

import org.junit.Test;

public class TierTest {
	@Test
	public void foo() throws Exception {
		Agent agent = new Agent();
		WorkOrder workOrder = new WorkOrder(1);
		Tier tier = new Tier(asList(agent));
		ArrayDeque<WorkOrder> workOrders = new ArrayDeque<WorkOrder>();
		workOrders.add(workOrder);
		tier.addUnclaimedWork(workOrders);

		assertFalse(Agent.hasWork().f(agent));
		tier.tick();
		assertTrue(Agent.hasWork().f(agent));
		assertFalse(workOrder.isWorkCompletedForTheTier());

		tier.tick();
		assertFalse(Agent.hasWork().f(agent));
		assertFalse(workOrder.isWorkCompletedForTheTier());
	}

	@Test
	public void two_tiers_together() throws Exception {
		Agent agent = new Agent();
		WorkOrder workOrder = new WorkOrder(1);
		Tier tier = new Tier(asList(agent));
		Tier tier2 = new Tier(asList(agent));
		ArrayDeque<WorkOrder> workOrders = new ArrayDeque<WorkOrder>();
		workOrders.add(workOrder);
		tier.addUnclaimedWork(workOrders);

		assertFalse(Agent.hasWork().f(agent));
		tier.tick();
		assertTrue(Agent.hasWork().f(agent));
		assertFalse(workOrder.isWorkCompletedForTheTier());

		Deque<WorkOrder> completedWork = tier.tick();
		assertFalse(Agent.hasWork().f(agent));
		assertFalse(workOrder.isWorkCompletedForTheTier());

		tier2.addUnclaimedWork(completedWork);
		tier2.tick();
		assertTrue(Agent.hasWork().f(agent));
		assertFalse(workOrder.isWorkCompletedForTheTier());

		tier2.tick();
		assertFalse(Agent.hasWork().f(agent));
		assertFalse(workOrder.isWorkCompletedForTheTier());
	}
}
