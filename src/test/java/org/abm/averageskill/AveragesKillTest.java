package org.abm.averageskill;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AveragesKillTest {
	// Instead of thinking of this thing as multi tier think of it as fucking singly tier
	// Where you can pipe together arbitraty tiers a tier has a work queue and outputs a work queue

	@Test
	public void when_the_simulation_has_no_work_to_do_it_is_over_immediately() throws Exception {
		AveragesKill simulation = new AveragesKill();
		WorkOrder workOrder = new WorkOrder(0);
		workOrder.markAllTiersHaveCompletedWorkingOnThis();
		WorkOrders workOrders = new WorkOrders(workOrder);

		int timeTook = simulation.run(workOrders, new Agents(asList(new Agent())));
		assertEquals(0, timeTook);
	}

	@Test
	public void with_one_work_order_that_needs_1_piece_of_work_it_takes_three_time_units() throws Exception {
		AveragesKill simulation = new AveragesKill();
		WorkOrders workOrders = new WorkOrders(new WorkOrder(1));
		Agent[][] agentsAtTiers = new Agent[1][1];
		agentsAtTiers[0][0] = new Agent();

		int timeTook = simulation.run(workOrders, new Agents(asList(new Agent())));
		assertEquals(3, timeTook);
	}

	@Test
	public void one_agent_two_work_orders() throws Exception {
		AveragesKill simulation = new AveragesKill(20);
		WorkOrders workOrders = new WorkOrders(new WorkOrder(1), new WorkOrder(1));

		int timeTook = simulation.run(workOrders, new Agents(asList(new Agent())));
		assertEquals(6, timeTook);
	}

}
