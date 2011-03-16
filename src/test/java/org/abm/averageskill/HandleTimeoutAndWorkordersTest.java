package org.abm.averageskill;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.abm.averageskill.AverageTimeToProcessWorkOrdersTest.AverageTimeToProcessAllWorkOrders;
import org.abm.averageskill.event.QueueBasedEventSource;
import org.abm.averageskill.event.TickEvent;
import org.abm.averageskill.event.WorkOrderCompletedEvent;
import org.junit.Test;

public class HandleTimeoutAndWorkordersTest {

	// NOTE if the tick events are added after the WOCEs then this test will fail... that's highly suspect
	@Test
	public void when_the_time_of_the_next_event_is_after_the_timeout_the_simulation_terminates() throws Exception {
		int timeout = 2;
		int expectedNumberOfWorkOrdersToComplete = 2;
		WorkOrderCompletionMonitor workOrderCompletionMonitotor = new WorkOrderCompletionMonitor(expectedNumberOfWorkOrdersToComplete, mock(AverageTimeToProcessAllWorkOrders.class));
		TickListeningTimeoutMonitor timeoutMonitor = new TickListeningTimeoutMonitor(timeout);

		QueueBasedEventSource workOrderCompletedEventSource = new QueueBasedEventSource(workOrderCompletionMonitotor, timeoutMonitor);

		workOrderCompletedEventSource.notifyOfEvent(TickEvent.at(1));
		workOrderCompletedEventSource.notifyOfEvent(WorkOrderCompletedEvent.at(1));
		workOrderCompletedEventSource.notifyOfEvent(TickEvent.at(10));
		workOrderCompletedEventSource.notifyOfEvent(WorkOrderCompletedEvent.at(10));

		int actualTime = workOrderCompletedEventSource.run();

		assertEquals(1, actualTime);
	}

}
