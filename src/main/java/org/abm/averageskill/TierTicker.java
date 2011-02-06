package org.abm.averageskill;

import java.util.List;

public interface TierTicker {
	public List<WorkOrder> tickTier(AgentsInATier agents, List<WorkOrder> workOrders);
}