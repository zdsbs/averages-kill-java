package org.abm.averageskill;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.abm.averageskill.event.SimulationTerminatedEvent;
import org.abm.averageskill.simulation.SimulationTerminationListener;
import org.abm.averageskill.simulation.Tickee;
import org.junit.Test;
import org.mockito.InOrder;

public class TicksUntilItShouldnt {
	public class Ticker implements SimulationTerminationListener {
		private boolean done = false;
		int time = 0;

		public Ticker() {
		}

		@Override
		public void onTermination(SimulationTerminatedEvent event) {
			done = true;
		}

		public void run(Tickee... tickees) {
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
		final Ticker ticker = new Ticker();

		ticker.run(someOtherTickee, new Tickee() {

			@Override
			public void tick(int atTime) {
				if (atTime >= 3)
					ticker.onTermination(SimulationTerminatedEvent.at(atTime));
			}
		});

		InOrder inOrder = inOrder(someOtherTickee);
		inOrder.verify(someOtherTickee).tick(0);
		inOrder.verify(someOtherTickee).tick(1);
		inOrder.verify(someOtherTickee).tick(2);
		inOrder.verify(someOtherTickee).tick(3);
		verifyNoMoreInteractions(someOtherTickee);
	}

}
