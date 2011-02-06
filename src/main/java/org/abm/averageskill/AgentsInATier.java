package org.abm.averageskill;

import static org.abm.averageskill.Agent.hasNoWork;
import static org.abm.averageskill.Agent.hasWork;
import static org.abm.averageskill.Agent.haveCompletedTheirWork;
import static org.abm.averageskill.Lists.filter;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

public class AgentsInATier {
	private final Agent[][] agentsAtTiers;

	AgentsInATier(Agent[][] agentsAtTiers) {
		this.agentsAtTiers = agentsAtTiers;
	}

	private List<Agent> allAgents() {
		List<Agent> allAgents = new ArrayList<Agent>();
		for (Agent[] tier : agentsAtTiers) {
			allAgents.addAll(Arrays.asList(tier));
		}
		return allAgents;
	}

	public List<Agent> getAgentsThatPassWorkOn() {
		return filter(haveCompletedTheirWork(), allAgents());
	}

	public List<Agent> getAgentsThatDoSomeWork() {
		return filter(hasWork(), allAgents());
	}

	public List<Agent> getAgentsThatAcceptOrders() {
		return filter(hasNoWork(), allAgents());
	}

	public void passOnWork(List<Agent> thatAcceptOrders) {
		for (Agent agent : thatAcceptOrders) {
			agent.markAllTiersHaveCompletedWorkingOnThis();
		}
	}

	public void doSomeWork(List<Agent> thatDoSomeWork) {
		for (Agent agent : thatDoSomeWork) {
			agent.doSomeWork();
		}
	}

	public void acceptOrders(List<Agent> thatAcceptOrders, List<WorkOrder> workOrders) {
		Deque<WorkOrder> order = new ArrayDeque<WorkOrder>(workOrders);
		for (Agent agentsWithNoWork : thatAcceptOrders) {
			if (order.isEmpty()) {
				return;
			}
			agentsWithNoWork.recieve(order.pop());
		}
	}

	public WorkOrders getCompletedWork() {
		throw new NotYetImplemented();
	}

}