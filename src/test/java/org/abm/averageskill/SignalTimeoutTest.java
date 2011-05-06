package org.abm.averageskill;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import lombok.RequiredArgsConstructor;

import org.abm.averageskill.HandleWorkOrderCompletedEventsTest.IRespondToTick;
import org.abm.averageskill.HandleWorkOrderCompletedEventsTest.TimeoutEvent;
import org.junit.Test;

public class SignalTimeoutTest {
	private final TimeoutListener timeoutListener = mock(TimeoutListener.class);
	private final TimeoutMonitor timeoutMonitor = new TimeoutMonitor(3, timeoutListener);

	@RequiredArgsConstructor
	public static class TimeoutMonitor implements IRespondToTick {
		private final int timeout;
		private final TimeoutListener timeoutListener;

		@Override
		public void tick(int atTime) {
			if (atTime >= timeout)
				timeoutListener.onTimeout(TimeoutEvent.at(atTime));
		}
	}

	public interface TimeoutListener {
		void onTimeout(TimeoutEvent at);
	}

	@Test
	public void timeout_is_reached_exactly() throws Exception {
		timeoutMonitor.tick(3);
		verify(timeoutListener).onTimeout(TimeoutEvent.at(3));
	}

	@Test
	public void timeout_is_not_yet_reached() throws Exception {
		timeoutMonitor.tick(anythingLessThan(3));
		verify(timeoutListener, never()).onTimeout(any(TimeoutEvent.class));
	}

	// Should this actually stop at 3 instead of 4?
	@Test
	public void timeout_has_passed() throws Exception {
		timeoutMonitor.tick(1);
		timeoutMonitor.tick(2);
		timeoutMonitor.tick(4);
		verify(timeoutListener).onTimeout(TimeoutEvent.at(4));
	}

	private int anythingLessThan(int upperBound) {
		return upperBound - 1;
	}
}
