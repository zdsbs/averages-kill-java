package org.abm.averageskill;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayDeque;
import java.util.Deque;

import org.junit.Test;

public class LastTierTest {
	@Test
	public void foo() throws Exception {
		Agent agent = new Agent();
		WorkOrder workOrder = new WorkOrder(1);
		LastTier tier = new LastTier(asList(agent));
		ArrayDeque<WorkOrder> workOrders = new ArrayDeque<WorkOrder>();
		workOrders.add(workOrder);
		tier.addUnclaimedWork(workOrders);

		assertFalse(Agent.hasWork().f(agent));
		tier.tick();
		assertTrue(Agent.hasWork().f(agent));
		assertFalse(workOrder.isWorkCompletedForTheTier());

		tier.tick();
		assertFalse(Agent.hasWork().f(agent));
		assertFalse(workOrder.isTotallyDone());

		tier.tick();
		assertTrue(workOrder.isTotallyDone());
	}

	@Test
	public void bar() throws Exception {
		Agent agent1 = new Agent();
		Agent agent2 = new Agent();
		WorkOrder workOrder = new WorkOrder(1);
		Tier tier = new Tier(asList(agent1));
		LastTier lastTier = new LastTier(asList(agent2));
		ArrayDeque<WorkOrder> workOrders = new ArrayDeque<WorkOrder>();
		workOrders.add(workOrder);
		tier.addUnclaimedWork(workOrders);

		Deque<WorkOrder> newWork = tick(tier, lastTier);
		assertFalse(workOrder.isTotallyDone());
		assertTrue(newWork.isEmpty());

		newWork = tick(tier, lastTier);
		assertFalse(workOrder.isTotallyDone());
		assertFalse(newWork.isEmpty());

		newWork = tick(tier, lastTier);
		assertFalse(workOrder.isTotallyDone());
		assertTrue(newWork.isEmpty());

		newWork = tick(tier, lastTier);
		assertFalse(workOrder.isTotallyDone());
		assertTrue(newWork.isEmpty());
	}

	private Deque<WorkOrder> tick(Tier tier, LastTier lastTier) {
		Deque<WorkOrder> newWork = tier.tick();
		lastTier.addUnclaimedWork(newWork);
		lastTier.tick();
		return newWork;
	}

}
