package org.abm.averageskill;

import static org.abm.averageskill.Lists.filter;

import java.util.ArrayList;
import java.util.List;

public class AgentTier {
	private Agents agents;
	private final List<WorkOrder> workOrders = new ArrayList<WorkOrder>();

	public List<WorkOrder> tickTier() {

		List<Agent> thatPassWorkOn = agents.getAgentsThatPassWorkOn();
		List<Agent> thatDoSomeWork = agents.getAgentsThatDoSomeWork();
		List<Agent> thatAcceptOrders = agents.getAgentsThatAcceptOrders();

		agents.addWorkOrders(filter(WorkOrder.isNotComplete(), workOrders));
		agents.passOnWork(thatPassWorkOn);
		agents.doSomeWork(thatDoSomeWork);
		agents.acceptOrders(thatAcceptOrders);

		return agents.getCompletedWork();
	}

	public void addWork(List<WorkOrder> notComplete) {
		workOrders.addAll(notComplete);
	}

	public void setAgents(Agents agents) {
		this.agents = agents;
	}

}