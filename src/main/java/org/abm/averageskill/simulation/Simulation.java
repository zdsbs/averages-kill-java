package org.abm.averageskill.simulation;

import org.abm.averageskill.event.AllWorkCompletedEvent;
import org.abm.averageskill.event.SimulationTerminatedEvent;
import org.abm.averageskill.event.TimeoutEvent;

// What can I ignore right now / or what's the weakest assumption I can make
// right now.
// Better yet when I start making an assumption ask myself can I make a
// weaker assumption!

//Is this a simulation
public class Simulation implements TimeoutListener {
	private boolean stopped = false;
	private final SimulationTerminationListener terminationListener;

	public Simulation(SimulationTerminationListener terminationListener) {
		this.terminationListener = terminationListener;
	}

	private void stopTheSimulationAtUnlessItAlreadyStopped(int stoppingTime) {
		if (!stopped) {
			terminationListener.onTermination(SimulationTerminatedEvent.at(stoppingTime));
			stopped = true;
		}
	}

	public void onWorkCompleted(AllWorkCompletedEvent event) {
		stopTheSimulationAtUnlessItAlreadyStopped(event.getTicks());
	}

	@Override
	public void onTimeout(TimeoutEvent event) {
		stopTheSimulationAtUnlessItAlreadyStopped(event.getTicks());
	}
}