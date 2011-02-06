package org.abm.averageskill;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;

public class AveragesKillTest {
	// Instead of thinking of this thing as multi tier think of it as fucking singly tier
	// Where you can pipe together arbitraty tiers a tier has a work queue and outputs a work queue

	@Test
	public void when_the_simulation_has_no_work_to_do_it_is_over_immediately() throws Exception {
		Simulation simulation = new Simulation(mock(TierTicker.class));
		WorkOrder workOrder = new WorkOrder(0);
		workOrder.markAllTiersHaveCompletedWorkingOnThis();
		WorkOrders workOrders = new WorkOrders(workOrder);
		Agent[][] agentsAtTiers = new Agent[1][1];
		agentsAtTiers[0][0] = new Agent();

		int timeTook = simulation.run(new AgentsInATier(agentsAtTiers), workOrders);
		assertEquals(0, timeTook);
	}

	@Test
	public void sends_all_work_to_the_ticker() throws Exception {
		TierTicker ticker = mock(TierTicker.class);
		Simulation simulation = new Simulation(ticker, 1);
		WorkOrders workOrders = new WorkOrders(new WorkOrder(1));
		AgentsInATier agents = null;
		simulation.run(agents, workOrders);
		verify(ticker).tickTier(agents, workOrders);
	}

	@Test
	public void with_one_work_order_that_needs_1_piece_of_work_it_takes_three_time_units() throws Exception {
		Simulation simulation = new Simulation(new TickerDelegatesToAgents());
		WorkOrders workOrders = new WorkOrders(new WorkOrder(1));
		Agent[][] agentsAtTiers = new Agent[1][1];
		agentsAtTiers[0][0] = new Agent();

		int timeTook = simulation.run(new AgentsInATier(agentsAtTiers), workOrders);
		assertEquals(3, timeTook);
	}

	@Test
	public void one_agent_two_work_orders() throws Exception {
		Simulation simulation = new Simulation(new TickerDelegatesToAgents(), 20);
		WorkOrders workOrders = new WorkOrders(new WorkOrder(1), new WorkOrder(1));
		Agent[][] agentsAtTiers = new Agent[1][1];
		agentsAtTiers[0][0] = new Agent();

		int timeTook = simulation.run(new AgentsInATier(agentsAtTiers), workOrders);
		assertEquals(6, timeTook);
	}

}
