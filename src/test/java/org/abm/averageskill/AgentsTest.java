package org.abm.averageskill;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class AgentsTest {
	@Test
	public void withOneAgent() throws Exception {
		Agents agents = oneAgentOneTier();
		WorkOrder workOrder = new WorkOrder(1);
		agents.accept(asList(workOrder));
		agents.work();
		assertTrue(workOrder.workUnitsAreComplete());
	}

	@Test
	public void when_agents_already_have_some_work_they_will_not_accept_any_new_work() throws Exception {
		Agents agents = oneAgentOneTier();
		WorkOrder workOrder = new WorkOrder(1);
		WorkOrder workOrderThatWillNotBeAccepted = new WorkOrder(1);
		agents.accept(asList(workOrder));
		agents.accept(asList(workOrderThatWillNotBeAccepted));
		agents.work();
		assertTrue(workOrder.workUnitsAreComplete());
		assertFalse(workOrderThatWillNotBeAccepted.workUnitsAreComplete());
	}

	@Test
	public void when_there_is_more_work_than_agents_available_the_work_just_builds_up() throws Exception {
		Agents agents = oneAgentOneTier();
		WorkOrder workOrder = new WorkOrder(1);
		WorkOrder workOrderThatWillNotBeAccepted = new WorkOrder(1);
		agents.accept(asList(workOrder, workOrderThatWillNotBeAccepted));
		agents.work();
		assertTrue(workOrder.workUnitsAreComplete());
		assertFalse(workOrderThatWillNotBeAccepted.workUnitsAreComplete());
	}

	@Test
	public void more_agents_than_work() throws Exception {
		Agents agents = twoAgentsOneTier();
		WorkOrder workOrder = new WorkOrder(1);
		agents.accept(asList(workOrder));
		agents.work();
		assertTrue(workOrder.workUnitsAreComplete());
	}

	@Test
	public void two_agents_two_work_orders_all_is_good() throws Exception {
		Agents agents = twoAgentsOneTier();
		WorkOrder workOrder1 = new WorkOrder(1);
		WorkOrder workOrder2 = new WorkOrder(1);
		agents.accept(asList(workOrder1, workOrder2));
		agents.work();
		assertTrue(workOrder1.workUnitsAreComplete());
		assertTrue(workOrder2.workUnitsAreComplete());
	}

	@Test
	public void passWorkOn() throws Exception {
		fail();
	}

	private Agents oneAgentOneTier() {
		Agent[][] agentsInTiers = new Agent[1][1];
		agentsInTiers[0][0] = new Agent();
		Agents agents = new Agents(agentsInTiers);
		return agents;
	}

	private Agents twoAgentsOneTier() {
		Agent[][] agentsInTiers = new Agent[1][2];
		agentsInTiers[0][0] = new Agent();
		agentsInTiers[0][1] = new Agent();
		Agents agents = new Agents(agentsInTiers);
		return agents;
	}

}
