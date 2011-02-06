package org.abm.averageskill;

import static org.abm.averageskill.Agent.hasNoWork;
import static org.abm.averageskill.Agent.hasWork;
import static org.abm.averageskill.Agent.haveCompletedTheirWork;
import static org.abm.averageskill.Lists.filter;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class AgentsInATier {
	private final List<Agent> agentsAtTiers;

	AgentsInATier(List<Agent> agentsAtTiers) {
		this.agentsAtTiers = agentsAtTiers;
	}

	public List<Agent> getAgentsThatPassWorkOn() {
		return filter(haveCompletedTheirWork(), agentsAtTiers);
	}

	public List<Agent> getAgentsThatDoSomeWork() {
		return filter(hasWork(), agentsAtTiers);
	}

	public List<Agent> getAgentsThatAcceptOrders() {
		return filter(hasNoWork(), agentsAtTiers);
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

	public List<WorkOrder> getCompletedWork() {
		throw new NotYetImplemented();
	}

}