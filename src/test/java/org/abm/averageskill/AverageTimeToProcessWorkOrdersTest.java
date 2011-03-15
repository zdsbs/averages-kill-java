package org.abm.averageskill;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.abm.averageskill.event.NotifiableWorkOrderCompletedEventSource;
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
			for (WorkOrderCompletedEvent event : completedEvents) {
				sum += event.getTicks();
			}

			return sum / completedEvents.size();
		}
	}

	@Test
	public void average_time_to_complete_work_order_is_just_the_time_it_took_to_complete_for_one_work_order_assuming_they_all_start_at_0() throws Exception {
		AverageTimeToProcessAllWorkOrders averageTimeToProcessAllWorkOrders = new AverageTimeToProcessAllWorkOrders();
		AveragesKill averagesKill = new AveragesKill(1, averageTimeToProcessAllWorkOrders);
		NotifiableWorkOrderCompletedEventSource eventSource = new NotifiableWorkOrderCompletedEventSource(averagesKill, 5);
		eventSource.notifyOfCompletedEvent(WorkOrderCompletedEvent.at(3));
		eventSource.run();
		assertEquals(3, averageTimeToProcessAllWorkOrders.is());
	}

	@Test
	public void average_time_to_complete_works_just_fine_with_a_few_work_orders() throws Exception {
		AverageTimeToProcessAllWorkOrders averageTimeToProcessAllWorkOrders = new AverageTimeToProcessAllWorkOrders();
		AveragesKill averagesKill = new AveragesKill(2, averageTimeToProcessAllWorkOrders);
		NotifiableWorkOrderCompletedEventSource eventSource = new NotifiableWorkOrderCompletedEventSource(averagesKill, 5);
		eventSource.notifyOfCompletedEvent(WorkOrderCompletedEvent.at(1));
		eventSource.notifyOfCompletedEvent(WorkOrderCompletedEvent.at(4));
		eventSource.run();
		assertEquals(2, averageTimeToProcessAllWorkOrders.is());
	}
}
