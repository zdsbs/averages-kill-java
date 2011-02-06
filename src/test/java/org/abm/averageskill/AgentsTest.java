package org.abm.averageskill;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

public class AgentsTest {
	@Test
	public void withOneAgent() throws Exception {
		Agents agents = oneAgentOneTier();
		WorkOrder workOrder = new WorkOrder(1);
		agents.addWorkOrders(asList(workOrder));
		agents.acceptOrders(agents.getAgentsThatAcceptOrders());
		agents.doSomeWork(agents.getAgentsThatDoSomeWork());
		assertTrue(workOrder.workCompletedForTheTier());
	}

	@Test
	public void when_agents_already_have_some_work_they_will_not_accept_any_new_work() throws Exception {
		Agents agents = oneAgentOneTier();
		WorkOrder workOrder = new WorkOrder(1);
		WorkOrder workOrderThatWillNotBeAccepted = new WorkOrder(1);
		agents.addWorkOrders(asList(workOrder, workOrderThatWillNotBeAccepted));
		agents.acceptOrders(agents.getAgentsThatAcceptOrders());
		agents.acceptOrders(agents.getAgentsThatAcceptOrders());
		agents.doSomeWork(agents.getAgentsThatDoSomeWork());
		assertTrue(workOrder.workCompletedForTheTier());
		assertFalse(workOrderThatWillNotBeAccepted.workCompletedForTheTier());
	}

	@Test
	public void when_there_is_more_work_than_agents_available_the_work_just_builds_up() throws Exception {
		Agents agents = oneAgentOneTier();
		WorkOrder workOrder = new WorkOrder(1);
		WorkOrder workOrderThatWillNotBeAccepted = new WorkOrder(1);
		agents.addWorkOrders(asList(workOrder, workOrderThatWillNotBeAccepted));
		agents.acceptOrders(agents.getAgentsThatAcceptOrders());

		agents.doSomeWork(agents.getAgentsThatDoSomeWork());
		assertTrue(workOrder.workCompletedForTheTier());
		assertFalse(workOrderThatWillNotBeAccepted.workCompletedForTheTier());
	}

	@Test
	public void more_agents_than_work() throws Exception {
		Agents agents = twoAgentsOneTier();
		WorkOrder workOrder = new WorkOrder(1);
		agents.addWorkOrders(asList(workOrder));
		agents.acceptOrders(agents.getAgentsThatAcceptOrders());
		agents.doSomeWork(agents.getAgentsThatDoSomeWork());
		assertTrue(workOrder.workCompletedForTheTier());
	}

	@Test
	public void two_agents_two_work_orders_all_is_good() throws Exception {
		Agents agents = twoAgentsOneTier();
		WorkOrder workOrder1 = new WorkOrder(1);
		WorkOrder workOrder2 = new WorkOrder(1);
		agents.addWorkOrders(asList(workOrder1, workOrder2));
		agents.acceptOrders(agents.getAgentsThatAcceptOrders());
		agents.doSomeWork(agents.getAgentsThatDoSomeWork());
		assertTrue(workOrder1.workCompletedForTheTier());
		assertTrue(workOrder2.workCompletedForTheTier());
	}

	@Test
	public void gettingCompletedWork_returns_all_the_finished_work() throws Exception {
		Agents agents = twoAgentsOneTier();
		WorkOrder workOrder1 = new WorkOrder(1);
		WorkOrder workOrder2 = new WorkOrder(1);
		agents.addWorkOrders(asList(workOrder1, workOrder2));
		agents.acceptOrders(agents.getAgentsThatAcceptOrders());
		agents.doSomeWork(agents.getAgentsThatDoSomeWork());
		assertEquals(asList(workOrder1, workOrder2), agents.getCompletedWork());
	}

	@Test
	public void adding_work_does_not_add_the_same_piece_of_work() throws Exception {
		Agents agents = new Agents(new ArrayList<Agent>());
		WorkOrder workOrder = new WorkOrder(1);
		agents.addWorkOrders(asList(workOrder));
		agents.addWorkOrders(asList(workOrder));
		agents.addWorkOrders(asList(workOrder));
		agents.addWorkOrders(asList(workOrder));
		assertEquals(asList(workOrder), agents.getCurrentWorkInTier());
	}

	private Agents oneAgentOneTier() {
		Agents agents = new Agents(asList(new Agent()));
		return agents;
	}

	private Agents twoAgentsOneTier() {
		Agents agents = new Agents(asList(new Agent(), new Agent()));
		return agents;
	}

}
