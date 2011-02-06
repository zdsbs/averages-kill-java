package org.abm.averageskill;

import static org.abm.averageskill.Agent.hasNoWork;
import static org.abm.averageskill.Agent.hasWork;
import static org.abm.averageskill.Agent.haveCompletedTheirWork;
import static org.abm.averageskill.Lists.filter;

import java.util.ArrayList;
import java.util.List;

public class Agents {
	private final List<Agent> agentsAtTiers;
	private final List<WorkOrder> currentWorkInTier = new ArrayList<WorkOrder>();

	public Agents(List<? extends Agent> agentsAtTiers) {
		this.agentsAtTiers = new ArrayList<Agent>(agentsAtTiers);
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

	public void doSomeWork(List<Agent> thatDoSomeWork) {
		for (Agent agent : thatDoSomeWork) {
			agent.doSomeWork();
		}
	}

	public void giveAgentsWork(List<WorkOrder> workOrders) {
		for (WorkOrder workOrder : workOrders) {
			if (!this.currentWorkInTier.contains(workOrder)) {
				this.currentWorkInTier.add(workOrder);
			}
		}
	}

	public List<WorkOrder> getCurrentWorkInTier() {
		return new ArrayList<WorkOrder>(currentWorkInTier);
	}

	@Override
	public String toString() {
		return "agentsAtTiers=" + agentsAtTiers + "\ncurrentWorkInTier=" + currentWorkInTier;
	}

	public List<Agent> allAgents() {
		return agentsAtTiers;
	}

}