package org.abm.averageskill;


public class Simulation {
	private final Ticker ticker;
	private final int maxNumberOfTicks;

	Simulation(Ticker ticker) {
		this.ticker = ticker;
		this.maxNumberOfTicks = Integer.MAX_VALUE;
	}

	Simulation(Ticker ticker, int maxNumberOfTicks) {
		this.ticker = ticker;
		this.maxNumberOfTicks = maxNumberOfTicks;
	}

	public int run(Agents agents, WorkOrders workOrders) {
		int timeTook = 0;
		while (!workOrders.haveAllTiersHaveCompletedWorkingOnThis() && timeTook < maxNumberOfTicks) {
			ticker.tick(agents, workOrders);
			timeTook++;
		}
		return timeTook;
	}

}