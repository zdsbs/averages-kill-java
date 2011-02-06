package org.abm.averageskill;

import java.util.List;

public class TickerDelegatesToAgents implements TierTicker {

	@Override
	public List<WorkOrder> tickTier(AgentsInATier coordination, List<WorkOrder> inComingWorkOrders) {
		List<Agent> thatPassWorkOn = coordination.getAgentsThatPassWorkOn();
		List<Agent> thatDoSomeWork = coordination.getAgentsThatDoSomeWork();
		List<Agent> thatAcceptOrders = coordination.getAgentsThatAcceptOrders();

		coordination.passOnWork(thatPassWorkOn);
		coordination.doSomeWork(thatDoSomeWork);
		coordination.acceptOrders(thatAcceptOrders, inComingWorkOrders);

		return coordination.getCompletedWork();
	}
}