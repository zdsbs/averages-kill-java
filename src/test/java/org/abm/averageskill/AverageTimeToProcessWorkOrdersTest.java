package org.abm.averageskill;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.abm.averageskill.event.Event;
import org.abm.averageskill.event.QueueBasedEventSource;
import org.abm.averageskill.event.WorkOrderCompletedEvent;
import org.junit.Test;

public class AverageTimeToProcessWorkOrdersTest {
	// we're going to need to know when a work order starts as well

	public static class AverageTimeToProcessAllWorkOrders {
		private final List<WorkOrderCompletedEvent> completedEvents = new ArrayList<WorkOrderCompletedEvent>();

		public void onWorkOrderCompleted(WorkOrderCompletedEvent workOrderCompletedEvent) {
			completedEvents.add(workOrderCompletedEvent);
		}

		public int is() {
			if (completedEvents.isEmpty()) {
				return 0;
			}
			int sum = 0;
			for (Event event : completedEvents) {
				sum += event.getTicks();
			}

			return sum / completedEvents.size();
		}
	}

	@Test
	public void average_time_to_complete_work_order_is_just_the_time_it_took_to_complete_for_one_work_order_assuming_they_all_start_at_0() throws Exception {
		AverageTimeToProcessAllWorkOrders averageTimeToProcessAllWorkOrders = new AverageTimeToProcessAllWorkOrders();
		WorkOrderCompletionMonitor averagesKill = new WorkOrderCompletionMonitor(1, averageTimeToProcessAllWorkOrders);
		QueueBasedEventSource eventSource = new QueueBasedEventSource(averagesKill, new NeverEndingTimeoutMonitor());
		eventSource.notifyOfEvent(WorkOrderCompletedEvent.at(3));
		eventSource.run();
		assertEquals(3, averageTimeToProcessAllWorkOrders.is());
	}

	@Test
	public void average_time_to_complete_works_just_fine_with_a_few_work_orders() throws Exception {
		AverageTimeToProcessAllWorkOrders averageTimeToProcessAllWorkOrders = new AverageTimeToProcessAllWorkOrders();
		WorkOrderCompletionMonitor averagesKill = new WorkOrderCompletionMonitor(2, averageTimeToProcessAllWorkOrders);
		QueueBasedEventSource eventSource = new QueueBasedEventSource(averagesKill, new NeverEndingTimeoutMonitor());
		eventSource.notifyOfEvent(WorkOrderCompletedEvent.at(1));
		eventSource.notifyOfEvent(WorkOrderCompletedEvent.at(4));
		eventSource.run();
		assertEquals(2, averageTimeToProcessAllWorkOrders.is());
	}
}
