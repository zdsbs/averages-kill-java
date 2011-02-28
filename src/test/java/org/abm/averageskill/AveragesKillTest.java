package org.abm.averageskill;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

public class AveragesKillTest {

	@Test
	@Ignore("We're not sure how to model work orders never completing")
	public void simulation_times_out_with_uncompleted_work_orders() throws Exception {
		int TIMEOUT = 2;
		AveragesKill simulation = new AveragesKill(TIMEOUT);
		// assertEquals(TIMEOUT, simulation.run());
	}

	@Test
	public void with_one_work_order_that_needs_1_piece_of_work_it_takes_three_time_units() throws Exception {
		AveragesKill simulation = new AveragesKill(20);
		WorkOrders workOrders = new WorkOrders(new WorkOrder(1));

		int timeTook = simulation.run(workOrders, new Agents(asList(new Agent())));
		assertEquals(3, timeTook);
	}

	@Test
	public void with_one_work_order_that_needs_10_piece_of_work_it_takes_three_time_units() throws Exception {
		AveragesKill simulation = new AveragesKill(20);
		WorkOrders workOrders = new WorkOrders(new WorkOrder(10));

		int timeTook = simulation.run(workOrders, new Agents(asList(new Agent())));
		assertEquals(12, timeTook);
	}

	@Test
	public void one_agent_two_work_orders() throws Exception {
		AveragesKill simulation = new AveragesKill(20);
		WorkOrders workOrders = new WorkOrders(new WorkOrder(1), new WorkOrder(1));

		int timeTook = simulation.run(workOrders, new Agents(asList(new Agent())));
		assertEquals(6, timeTook);
	}

	@Test
	public void one_agent_two_work_orders_with_10() throws Exception {
		AveragesKill simulation = new AveragesKill(2000);
		WorkOrders workOrders = new WorkOrders(new WorkOrder(10), new WorkOrder(10));

		int timeTook = simulation.run(workOrders, new Agents(asList(new Agent())));
		assertEquals(24, timeTook);
	}

	@Test
	public void two_tiers_agents_one_work_order() throws Exception {
		AveragesKill simulation = new AveragesKill(200000);
		WorkOrders workOrders = new WorkOrders(workOrders(1, 2));

		Agents tier1 = new Agents(asList(new Agent()));
		Agents tier2 = new Agents(asList(new Agent()));
		int timeTook = simulation.run(workOrders, tier1, tier2);
		assertEquals(6, timeTook);
	}

	@Test
	public void two_tiers_agents_one_work_order_with_10() throws Exception {
		AveragesKill simulation = new AveragesKill(200);
		WorkOrders workOrders = new WorkOrders(new WorkOrder(10));

		Agents tier1 = new Agents(asList(new Agent()));
		Agents tier2 = new Agents(asList(new Agent()));
		int timeTook = simulation.run(workOrders, tier1, tier2);
		assertEquals(22, timeTook);
	}

	@Test
	public void two_tiers_with_one_agent_each_two_work_size_two() throws Exception {
		AveragesKill simulation = new AveragesKill(200);
		WorkOrders workOrders = new WorkOrders(new WorkOrder(10), new WorkOrder(10));

		Agents tier1 = new Agents(asList(new Agent()));
		Agents tier2 = new Agents(asList(new Agent()));
		int timeTook = simulation.run(workOrders, tier1, tier2);
		assertEquals(10, timeTook);
	}

	@Test
	public void three_tiers() throws Exception {
		AveragesKill simulation = new AveragesKill(200);
		WorkOrders workOrders = new WorkOrders(new WorkOrder(10));

		Agents tier1 = new Agents(asList(new Agent()));
		Agents tier2 = new Agents(asList(new Agent()));
		Agents tier3 = new Agents(asList(new Agent()));
		int timeTook = simulation.run(workOrders, tier1, tier2, tier3);
		assertEquals(32, timeTook);
	}

	@Test
	public void three_tiers2() throws Exception {
		AveragesKill simulation = new AveragesKill(200);
		WorkOrders workOrders = new WorkOrders(new WorkOrder(2), new WorkOrder(2));

		Agents tier1 = new Agents(asList(new Agent()));
		Agents tier2 = new Agents(asList(new Agent()));
		Agents tier3 = new Agents(asList(new Agent()));
		int timeTook = simulation.run(workOrders, tier1, tier2, tier3);
		assertEquals(32, timeTook);
	}

	@Test
	public void lots_of_work() throws Exception {
		AveragesKill simulation = new AveragesKill(200);
		WorkOrders workOrders = new WorkOrders(new WorkOrder(10), new WorkOrder(10), new WorkOrder(10));

		Agents tier1 = new Agents(asList(new Agent()));
		Agents tier2 = new Agents(asList(new Agent()));
		Agents tier3 = new Agents(asList(new Agent()));
		int timeTook = simulation.run(workOrders, tier1, tier2, tier3);
		assertEquals(64, timeTook);
	}

	@Test
	public void lots_of_wor2k() throws Exception {
		AveragesKill simulation = new AveragesKill(200);
		WorkOrders workOrders = new WorkOrders(new WorkOrder(10), new WorkOrder(10), new WorkOrder(10), new WorkOrder(10), new WorkOrder(10));

		Agents tier1 = new Agents(asList(new Agent()));
		Agents tier2 = new Agents(asList(new Agent()));
		Agents tier3 = new Agents(asList(new Agent()));
		int timeTook = simulation.run(workOrders, tier1, tier2, tier3);
		assertEquals(64, timeTook);
	}

	@Test
	public void foo() throws Exception {
		AveragesKill simulation = new AveragesKill(2000000000);
		WorkOrder[] workOrders2 = workOrders(1000, 10);
		WorkOrders workOrders = new WorkOrders(workOrders2);

		Agents tier1 = new Agents(asList(new Agent()));
		Agents tier2 = new Agents(asList(new Agent()));
		int timeTook = simulation.run(workOrders, tier1, tier2);
		System.out.println(timeTook);
	}

	@Test
	public void foo_rand() throws Exception {
		AveragesKill simulation = new AveragesKill(2000000000);
		WorkOrder[] workOrders2 = workOrders(1000, 10);
		WorkOrders workOrders = new WorkOrders(workOrders2);

		Agents tier1 = new Agents(asList(new AgentRandom()));
		Agents tier2 = new Agents(asList(new AgentRandom()));
		int timeTook = simulation.run(workOrders, tier1, tier2);
		System.out.println(timeTook);
	}

	public WorkOrder[] workOrders(int number, int size) {
		WorkOrder[] orders = new WorkOrder[number];
		for (int i = 0; i < number; i++) {
			orders[i] = new WorkOrder(size);
		}
		return orders;
	}
}
