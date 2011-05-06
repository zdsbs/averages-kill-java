package org.abm.averageskill;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import lombok.RequiredArgsConstructor;

import org.abm.averageskill.HandleWorkOrderCompletedEventsTest.IRespondToTick;
import org.abm.averageskill.HandleWorkOrderCompletedEventsTest.TimeoutEvent;
import org.junit.Test;

public class TimeoutMonitorTest {
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
	public void when_the_monitor_hit_the_timeout_it_raises_the_timeoutEvent()
			throws Exception {

		TimeoutListener timeoutListener = mock(TimeoutListener.class);
		TimeoutMonitor timeoutMonitor = new TimeoutMonitor(3, timeoutListener);
		timeoutMonitor.tick(3);
		verify(timeoutListener).onTimeout(TimeoutEvent.at(3));
	}

	@Test
	public void when_the_monitor_is_notified_of_a_tick_that_is_before_the_time_it_does_nothing()
			throws Exception {

		TimeoutListener timeoutListener = mock(TimeoutListener.class);
		TimeoutMonitor timeoutMonitor = new TimeoutMonitor(3, timeoutListener);
		timeoutMonitor.tick(anythingLessThan(3));
		verify(timeoutListener, never()).onTimeout(any(TimeoutEvent.class));
	}
	
	//Should this actually stop at 3 instead of 4?
	@Test
	public void when_the_monitor_is_notified_of_a_tick_that_is_after_the_timeout_it_raises_the_timeoutEvent()
			throws Exception {

		TimeoutListener timeoutListener = mock(TimeoutListener.class);
		TimeoutMonitor timeoutMonitor = new TimeoutMonitor(3, timeoutListener);
		
		timeoutMonitor.tick(1);
		timeoutMonitor.tick(2);
		timeoutMonitor.tick(4);
		verify(timeoutListener).onTimeout(TimeoutEvent.at(4));
	}
	
	
	private int anythingLessThan(int upperBound) {
		return upperBound - 1;
	}
}
