package org.abm.averageskill;

import static org.junit.Assert.assertEquals;

import org.abm.averageskill.HandleWorkOrderCompletedEventsTest.WorkOrderCompletedEvent;
import org.junit.Test;

public class AverageTimeToProcessWorkOrdersTest {
	// we're going to need to know when a work order starts as well

	public static class AverageTimeToProcessAllWorkOrders {

		public int is() {
			return -1;
		}

	}

	private final class WorkOrderCompletedEventSourceFiresOff implements WorkOrderEventSource {
		private final AveragesKill averagesKill;
		private final WorkOrderCompletedEvent eventToFire;

		private WorkOrderCompletedEventSourceFiresOff(AveragesKill averagesKill, WorkOrderCompletedEvent eventToFire) {
			this.averagesKill = averagesKill;
			this.eventToFire = eventToFire;
		}

		@Override
		public void doWork() {
			averagesKill.onWorkOrderCompleted(eventToFire);
		}
	}

	@Test
	public void average_time_to_complete_work_order_is_just_the_time_it_took_to_complete_for_one_work_order() throws Exception {
		AverageTimeToProcessAllWorkOrders averageTimeToProcessAllWorkOrders = new AverageTimeToProcessAllWorkOrders();
		AveragesKill averagesKill = new AveragesKill(1000, 1);
		averagesKill.runWithEvents(new WorkOrderCompletedEventSourceFiresOff(averagesKill, WorkOrderCompletedEvent.at(3)));
		assertEquals(3, averageTimeToProcessAllWorkOrders.is());
	}
}
