package org.abm.averageskill.simulation;

import org.abm.averageskill.event.AllWorkCompletedEvent;
import org.abm.averageskill.event.TimeoutEvent;
import org.abm.averageskill.event.TimeoutListener;

public class Simulation implements TimeoutListener {
	private int haltingTime;

	public Simulation() {
	}

	private void stopTheSimulationAtUnlessItAlreadyStopped(int stoppingTime) {
		if (notAlreadyDone()) {
			haltingTime = stoppingTime;
		}
	}

	private boolean notAlreadyDone() {
		return haltingTime <= 0;
	}

	public int stoppedAt() {
		return haltingTime;
	}

	public void onWorkCompleted(AllWorkCompletedEvent event) {
		stopTheSimulationAtUnlessItAlreadyStopped(event.getTicks());
	}

	@Override
	public void onTimeout(TimeoutEvent event) {
		stopTheSimulationAtUnlessItAlreadyStopped(event.getTicks());
	}
}