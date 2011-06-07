package org.abm.averageskill;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.abm.averageskill.event.SimulationTerminatedEvent;
import org.abm.averageskill.simulation.Simulation;
import org.abm.averageskill.simulation.SimulationTerminationListener;
import org.abm.averageskill.simulation.Tickee;
import org.abm.averageskill.simulation.TimeoutMonitor;
import org.junit.Test;
import org.mockito.InOrder;

public class TicksUntilItShouldntTest {
	public class Ticker implements SimulationTerminationListener {
		private boolean done = false;
		private final Tickee[] tickees;
		int time = 0;

		public Ticker(Tickee... tickees) {
			this.tickees = tickees;
		}

		@Override
		public void onTermination(SimulationTerminatedEvent event) {
			done = true;
		}

		public void run() {
			while (!done) {
				for (Tickee tickee : tickees) {
					tickee.tick(time);
				}
				time++;
			}
		}

	}

	// Hah, yuck
	@Test
	public void timeout() throws Exception {
		// This setup is so confusing
		Tickee someOtherTickee = mock(Tickee.class);
		SimulationTerminationListener terminationListener = mock(SimulationTerminationListener.class);
		Simulation simulation = new Simulation(terminationListener);
		Tickee timeoutMonitor = new TimeoutMonitor(3, simulation);
		Ticker ticker = new Ticker(timeoutMonitor, someOtherTickee);

		simulation.addTerminationListener(ticker);
		ticker.run();

		InOrder inOrder = inOrder(someOtherTickee);
		inOrder.verify(someOtherTickee).tick(0);
		inOrder.verify(someOtherTickee).tick(1);
		inOrder.verify(someOtherTickee).tick(2);
		inOrder.verify(someOtherTickee).tick(3);
		verify(terminationListener).onTermination(SimulationTerminatedEvent.at(3));
	}
}
