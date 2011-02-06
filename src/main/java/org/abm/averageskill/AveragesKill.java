package org.abm.averageskill;

import java.util.ArrayList;
import java.util.List;

public class AveragesKill {
	private final List<AgentTier> tiers = new ArrayList<AgentTier>();
	private final int maxNumberOfTicks;

	AveragesKill() {
		this.maxNumberOfTicks = Integer.MAX_VALUE;
	}

	AveragesKill(int maxNumberOfTicks) {
		this.maxNumberOfTicks = maxNumberOfTicks;
	}

	public int run(WorkOrders allWorkOrders, Agents... agents) {
		int timeTook = 0;
		initializeTiers(agents);

		while (!allWorkOrders.haveAllTiersHaveCompletedWorkingOnThis() && timeTook < maxNumberOfTicks) {
			List<WorkOrder> needsWork = allWorkOrders.notComplete();
			for (AgentTier tierTicker : tiers) {
				tierTicker.addWork(needsWork);
				needsWork = tierTicker.tickTier();
			}
			timeTook++;
		}
		return timeTook;
	}

	private void initializeTiers(Agents[] agents) {
		for (Agents agentTier : agents) {
			AgentTier tickerDelegatesToAgents = new AgentTier();
			tickerDelegatesToAgents.setAgents(agentTier);
			tiers.add(tickerDelegatesToAgents);
		}
	}

}