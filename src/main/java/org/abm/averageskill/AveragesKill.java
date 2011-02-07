package org.abm.averageskill;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class AveragesKill {
	private final List<Tier> tiers = new ArrayList<Tier>();
	private final int maxNumberOfTicks;
	private final Log log;

	AveragesKill() {
		this.maxNumberOfTicks = Integer.MAX_VALUE;
		this.log = new LogNoOp();
	}

	AveragesKill(int maxNumberOfTicks) {
		this.maxNumberOfTicks = maxNumberOfTicks;
		this.log = new LogNoOp();
	}

	public int run(WorkOrders allWorkOrders, Agents... agents) {
		int timeTook = 0;
		initializeTiers(agents);

		List<WorkOrder> workForTheTier = allWorkOrders.getAll();
		Deque<WorkOrder> unclaimedWork = new ArrayDeque<WorkOrder>(workForTheTier);
		while (!allWorkOrders.isAllWorkTotallyDone() && timeTook < maxNumberOfTicks) {
			for (Tier tier : tiers) {
				tier.addUnclaimedWork(unclaimedWork);
				unclaimedWork = tier.tick();
			}
			timeTook++;
		}
		return timeTook;
	}

	private void initializeTiers(Agents[] agents) {
		for (int i = 0; i < agents.length - 1; i++) {
			Tier tier = new Tier(agents[i].allAgents(), i, log);
			tiers.add(tier);
		}

		tiers.add(new LastTier(agents[agents.length - 1].allAgents(), agents.length, log));
	}

}