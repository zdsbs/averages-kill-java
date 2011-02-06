package org.abm.averageskill;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


import org.junit.Test;

public class AveragesKillTest {

	@Test
	public void when_the_simulation_has_no_work_to_do_it_is_over_immediately() throws Exception {
		Simulation simulation = new Simulation(mock(Ticker.class));
		WorkOrders workOrders = new WorkOrders(new WorkOrder(0));
		Agent[][] agentsAtTiers = new Agent[1][1];
		agentsAtTiers[0][0] = new Agent();

		int timeTook = simulation.run(new Agents(agentsAtTiers), workOrders);
		assertEquals(0, timeTook);
	}

	@Test
	public void sends_all_work_to_the_ticker() throws Exception {
		Ticker ticker = mock(Ticker.class);
		Simulation simulation = new Simulation(ticker, 1);
		WorkOrders workOrders = new WorkOrders(new WorkOrder(1));
		Agents agents = null;
		simulation.run(agents, workOrders);
		verify(ticker).tick(agents, workOrders);
	}

	@Test
	public void with_one_work_order_that_needs_1_piece_of_work_it_takes_three_time_units() throws Exception {
		Simulation simulation = new Simulation(new TickerDelegatesToAgents());
		WorkOrders workOrders = new WorkOrders(new WorkOrder(1));
		Agent[][] agentsAtTiers = new Agent[1][1];
		agentsAtTiers[0][0] = new Agent();

		int timeTook = simulation.run(new Agents(agentsAtTiers), workOrders);
		assertEquals(3, timeTook);
	}

}
