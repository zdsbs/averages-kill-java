package org.abm.averageskill;

import java.util.*;

import org.abm.averageskill.HandleWorkOrderCompletedEventsTest.*;
import org.abm.averageskill.event.TimeoutListener;

public class Simulation implements TimeoutListener {
	private int haltingTime;
	private final Collection<IRespondToTick> tickListeners;

	public Simulation(Collection<IRespondToTick> workers) {
		this.tickListeners = workers;
	}

	public Simulation() {
		this(Collections.<IRespondToTick> emptyList());
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

	public static Simulation watching(IRespondToTick... workers) {
		return new Simulation(Arrays.<IRespondToTick> asList(workers));
	}

	public void tick(int i) {
		for (IRespondToTick each : tickListeners) {
			each.tick(i);
		}
	}

	public void onWorkCompleted(AllWorkCompletedEvent event) {
		stopTheSimulationAtUnlessItAlreadyStopped(event.getTicks());
	}

	public void onTimeout(TimeoutEvent event) {
		stopTheSimulationAtUnlessItAlreadyStopped(event.getTicks());
	}
}