package org.abm.averageskill;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.abm.averageskill.event.AllWorkCompletedEvent;
import org.abm.averageskill.event.SimulationTerminatedEvent;
import org.abm.averageskill.event.TimeoutEvent;
import org.abm.averageskill.simulation.Simulation;
import org.abm.averageskill.simulation.SimulationTerminationListener;
import org.junit.Ignore;
import org.junit.Test;

public class HandleWorkOrderCompletedEventsTest {
	// We're ignoring how work is partition
	@Test
	public void workCompletesEarlyEnough() throws Exception {
		SimulationTerminationListener terminationListener = mock(SimulationTerminationListener.class);
		Simulation simulation = new Simulation(terminationListener);
		simulation.onWorkCompleted(AllWorkCompletedEvent.at(1));

		verify(terminationListener).onTermination(SimulationTerminatedEvent.at(1));
	}

	@Test
	public void workNeverCompletes() throws Exception {
		SimulationTerminationListener terminationListener = mock(SimulationTerminationListener.class);
		Simulation simulation = new Simulation(terminationListener);
		// simulate work never being completed
		simulation.onTimeout(TimeoutEvent.at(2));
		verify(terminationListener).onTermination(SimulationTerminatedEvent.at(2));
	}

	@Test
	public void workCompletesTooLate() throws Exception {
		SimulationTerminationListener terminationListener = mock(SimulationTerminationListener.class);
		Simulation simulation = new Simulation(terminationListener);
		simulation.onTimeout(TimeoutEvent.at(2));
		simulation.onWorkCompleted(AllWorkCompletedEvent.at(3));

		verify(terminationListener).onTermination(SimulationTerminatedEvent.at(2));
	}

	@Test
	public void ignoresTimeoutEventComingTooLate() throws Exception {
		SimulationTerminationListener terminationListener = mock(SimulationTerminationListener.class);
		Simulation simulation = new Simulation(terminationListener);
		simulation.onWorkCompleted(AllWorkCompletedEvent.at(3));
		simulation.onTimeout(TimeoutEvent.at(5));

		verify(terminationListener).onTermination(SimulationTerminatedEvent.at(3));
		verify(terminationListener, never()).onTermination(SimulationTerminatedEvent.at(5));
	}

	@Test
	@Ignore("We don't know yet which client will care that the simulation is still running")
	public void simulationNotYetStopped() {
		// !??!?!
	}

}
