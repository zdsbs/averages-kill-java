package org.abm.averageskill.simulation;

import java.util.ArrayList;
import java.util.List;

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
	private final List<SimulationTerminationListener> terminationListeners = new ArrayList<SimulationTerminationListener>();

	public Simulation(SimulationTerminationListener terminationListener) {
		this.terminationListeners.add(terminationListener);
	}

	public Simulation() {
	}

	private void stopTheSimulationAtUnlessItAlreadyStopped(int stoppingTime) {
		if (!stopped) {
			for (SimulationTerminationListener terminationListener : terminationListeners) {
				terminationListener.onTermination(SimulationTerminatedEvent.at(stoppingTime));
			}
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

	public void addTerminationListener(SimulationTerminationListener terminationListener) {
		terminationListeners.add(terminationListener);
	}

	public Results run(Config config) {
		float time = 0;
		time += config.getCompletionTime();

		time += config.getTransitionTime();
		return new Results(config.getWorkOrders(), time);
	}
}