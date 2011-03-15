package org.abm.averageskill.event;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;

import org.abm.averageskill.WorkOrderCompletionMonitor;
import org.junit.Test;

public class QueueBasedEventSourceTest {
	@Test
	public void currentTime() throws Exception {
		QueueBasedEventSource eventSource = new QueueBasedEventSource(mock(WorkOrderCompletionMonitor.class), 1000000);
		eventSource.notifyOfCompletedEvent(WorkOrderCompletedEvent.at(100));
		assertEquals(0, eventSource.getCurrentTime());
		eventSource.doWork();
		assertEquals(100, eventSource.getCurrentTime());
	}

	@Test
	public void if_the_timeout_is_over_the_current_event_dont_fire_it_off() throws Exception {
		WorkOrderCompletionMonitor averagesKill = mock(WorkOrderCompletionMonitor.class);
		QueueBasedEventSource eventSource = new QueueBasedEventSource(averagesKill, 0);
		eventSource.notifyOfCompletedEvent(WorkOrderCompletedEvent.at(100));
		eventSource.doWork();
		verifyZeroInteractions(averagesKill);
	}

}
