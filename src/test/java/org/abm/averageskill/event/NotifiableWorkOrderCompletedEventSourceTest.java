package org.abm.averageskill.event;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;

import org.abm.averageskill.AveragesKill;
import org.junit.Test;

public class NotifiableWorkOrderCompletedEventSourceTest {
	@Test
	public void currentTime() throws Exception {
		NotifiableWorkOrderCompletedEventSource eventSource = new NotifiableWorkOrderCompletedEventSource(mock(AveragesKill.class), 1000000);
		eventSource.notifyOfCompletedEvent(WorkOrderCompletedEvent.at(100));
		assertEquals(0, eventSource.getCurrentTime());
		eventSource.doWork();
		assertEquals(100, eventSource.getCurrentTime());
	}

	@Test
	public void if_the_timeout_is_over_the_current_event_dont_fire_it_off() throws Exception {
		AveragesKill averagesKill = mock(AveragesKill.class);
		NotifiableWorkOrderCompletedEventSource eventSource = new NotifiableWorkOrderCompletedEventSource(averagesKill, 0);
		eventSource.notifyOfCompletedEvent(WorkOrderCompletedEvent.at(100));
		eventSource.doWork();
		verifyZeroInteractions(averagesKill);
	}

}
