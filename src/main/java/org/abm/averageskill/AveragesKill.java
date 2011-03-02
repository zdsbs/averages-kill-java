package org.abm.averageskill;

import org.abm.averageskill.AverageTimeToProcessWorkOrdersTest.AverageTimeToProcessAllWorkOrders;
import org.abm.averageskill.event.WorkOrderCompletedEvent;

public class AveragesKill {
	private final int maxNumberOfTicks;
	private int timeOfLastEvent;
	private final int expectedNumberOfWorkOrdersToComplete;
	private int nextEventAt;
	private int numberOfCompletedWorkItems = 0;
	private final AverageTimeToProcessAllWorkOrders averageTimeToProcessAllWorkOrders;

	public AveragesKill(int timeout, int expectedNumberOfWorkOrdersToComplete, AverageTimeToProcessAllWorkOrders averageTimeToProcessAllWorkOrders) {
		this.maxNumberOfTicks = timeout;
		this.expectedNumberOfWorkOrdersToComplete = expectedNumberOfWorkOrdersToComplete;
		this.averageTimeToProcessAllWorkOrders = averageTimeToProcessAllWorkOrders;
	}

	public int runWithEvents(WorkOrderEventSource workOrderCompletedEventSource) {
		while (!done()) {
			workOrderCompletedEventSource.doWork();
		}
		return timeOfLastEvent;
	}

	private boolean done() {
		if (nextEventAt > maxNumberOfTicks) {
			return true;
		}
		if (numberOfCompletedWorkItems >= expectedNumberOfWorkOrdersToComplete) {
			return true;
		}
		return false;
	}

	public void onWorkOrderCompleted(WorkOrderCompletedEvent event) {
		this.numberOfCompletedWorkItems++;
		this.timeOfLastEvent = event.getTicks();
		averageTimeToProcessAllWorkOrders.onWorkOrderCompleted(event);
	}

	// This is curious but it seems mightly convientent
	public void nextEventOccursAt(int nextEventAt) {
		this.nextEventAt = nextEventAt;
	}

}