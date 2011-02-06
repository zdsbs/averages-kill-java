package org.abm.averageskill;

import static org.abm.averageskill.Agent.hasNoWork;
import static org.abm.averageskill.Agent.hasWork;
import static org.abm.averageskill.Agent.haveCompletedTheirWork;
import static org.abm.averageskill.Lists.filter;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Agents {
	private final List<Agent> agentsAtTiers;
	private final List<WorkOrder> currentWorkInTier = new ArrayList<WorkOrder>();

	Agents(List<Agent> agentsAtTiers) {
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

	public void addWorkOrders(List<WorkOrder> workOrders) {
		for (WorkOrder workOrder : workOrders) {
			if (!this.currentWorkInTier.contains(workOrder)) {
				this.currentWorkInTier.add(workOrder);
			}
		}
	}

	public void acceptOrders(List<Agent> thatAcceptOrders) {
		Deque<WorkOrder> order = new ArrayDeque<WorkOrder>(getUnfinishedWork());
		for (Agent agentsWithNoWork : thatAcceptOrders) {
			if (order.isEmpty()) {
				return;
			}
			agentsWithNoWork.recieve(order.pop());
		}
	}

	public List<WorkOrder> getCompletedWork() {
		List<WorkOrder> completedWork = filter(WorkOrder.isComplete(), currentWorkInTier);
		return completedWork;
	}

	public List<WorkOrder> getUnfinishedWork() {
		List<WorkOrder> completedWork = filter(WorkOrder.isNotComplete(), currentWorkInTier);
		return completedWork;
	}

	public List<WorkOrder> getCurrentWorkInTier() {
		return new ArrayList<WorkOrder>(currentWorkInTier);
	}

}