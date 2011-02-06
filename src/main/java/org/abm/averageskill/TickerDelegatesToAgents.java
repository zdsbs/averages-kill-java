package org.abm.averageskill;

public class TickerDelegatesToAgents implements Ticker {

	// The Winkle - in the same time tick you cannot do all these actions to all agents.
	// you either need to keep track of which agents have done something in a tick
	// or you must buffer up the state effects till the end
	@Override
	public void tick(Agents agents, WorkOrders workOrders) {
		agents.markCompleteWorkAsCompleted();
		agents.passOnWork();
		agents.work();
		agents.accept(workOrders.notComplete());
	}

}