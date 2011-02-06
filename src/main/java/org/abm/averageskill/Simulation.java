package org.abm.averageskill;

public class Simulation {
	private final TierTicker ticker;
	private final int maxNumberOfTicks;

	Simulation(TierTicker ticker) {
		this.ticker = ticker;
		this.maxNumberOfTicks = Integer.MAX_VALUE;
	}

	Simulation(TierTicker ticker, int maxNumberOfTicks) {
		this.ticker = ticker;
		this.maxNumberOfTicks = maxNumberOfTicks;
	}

	public int run(AgentsInATier agents, WorkOrders allWorkOrders) {
		int timeTook = 0;
		while (!allWorkOrders.haveAllTiersHaveCompletedWorkingOnThis() && timeTook < maxNumberOfTicks) {

			ticker.tickTier(agents, allWorkOrders.notComplete());
			timeTook++;
		}
		return timeTook;
	}

}