package org.abm.averageskill;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.abm.averageskill.event.TimeoutEvent;
import org.abm.averageskill.simulation.TimeoutListener;
import org.abm.averageskill.simulation.TimeoutMonitor;
import org.junit.Test;

public class SignalTimeoutTest {
	private final TimeoutListener timeoutListener = mock(TimeoutListener.class);
	private final TimeoutMonitor timeoutMonitor = new TimeoutMonitor(3,timeoutListener);

	@Test
	public void timeout_is_reached_exactly() throws Exception {
		timeoutMonitor.tick(3);
		verify(timeoutListener).onTimeout(TimeoutEvent.at(3));
	}

	@Test
	public void timeout_is_not_yet_reached_no_ticks() throws Exception {
		verify(timeoutListener, never()).onTimeout(any(TimeoutEvent.class));
	}

	@Test
	public void timeout_is_not_yet_reached_one_tick() throws Exception {
		timeoutMonitor.tick(anythingLessThan(3));
		verify(timeoutListener, never()).onTimeout(any(TimeoutEvent.class));
	}

	@Test
	public void timeout_is_not_yet_reached_many_ticks() throws Exception {
		timeoutMonitor.tick(1);
		timeoutMonitor.tick(2);
		verify(timeoutListener, never()).onTimeout(any(TimeoutEvent.class));
	}

	// Should this actually stop at 3 instead of 4?
	@Test
	public void timeout_has_passed() throws Exception {
		timeoutMonitor.tick(4);
		verify(timeoutListener).onTimeout(TimeoutEvent.at(4));
	}

	private int anythingLessThan(int upperBound) {
		return upperBound - 1;
	}
}
