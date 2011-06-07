package org.abm.averageskill.simulation;

import org.abm.averageskill.event.SimulationTerminatedEvent;

public interface SimulationTerminationListener {
	public void onTermination(SimulationTerminatedEvent event);
}
