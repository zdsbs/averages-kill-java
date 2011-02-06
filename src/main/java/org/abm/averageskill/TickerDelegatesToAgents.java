package org.abm.averageskill;

import java.util.List;

public class TickerDelegatesToAgents implements TierTicker {

	@Override
	public WorkOrders tickTier(AgentsInATier coordination, WorkOrders inComingWorkOrders) {
		List<Agent> thatPassWorkOn = coordination.getAgentsThatPassWorkOn();
		List<Agent> thatDoSomeWork = coordination.getAgentsThatDoSomeWork();
		List<Agent> thatAcceptOrders = coordination.getAgentsThatAcceptOrders();

		coordination.passOnWork(thatPassWorkOn);
		coordination.doSomeWork(thatDoSomeWork);
		coordination.acceptOrders(thatAcceptOrders, inComingWorkOrders.notComplete());

		return coordination.getCompletedWork();
	}
}