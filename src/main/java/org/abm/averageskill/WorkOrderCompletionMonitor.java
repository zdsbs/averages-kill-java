package org.abm.averageskill;

import org.abm.averageskill.AverageTimeToProcessWorkOrdersTest.AverageTimeToProcessAllWorkOrders;
import org.abm.averageskill.event.WorkOrderCompletedEvent;

public class WorkOrderCompletionMonitor {
	// 3-15 - This is nice, this class is evolving to just check to see when all the work orders have been completed
	private final int expectedNumberOfWorkOrdersToComplete;
	private int numberOfCompletedWorkItems = 0;
	private final AverageTimeToProcessAllWorkOrders averageTimeToProcessAllWorkOrders;

	public WorkOrderCompletionMonitor(int expectedNumberOfWorkOrdersToComplete, AverageTimeToProcessAllWorkOrders averageTimeToProcessAllWorkOrders) {
		this.expectedNumberOfWorkOrdersToComplete = expectedNumberOfWorkOrdersToComplete;
		this.averageTimeToProcessAllWorkOrders = averageTimeToProcessAllWorkOrders;
	}

	public boolean hasCompletedAllWorkOrders() {
		if (numberOfCompletedWorkItems >= expectedNumberOfWorkOrdersToComplete) {
			return true;
		}
		return false;
	}

	public void onWorkOrderCompleted(WorkOrderCompletedEvent event) {
		this.numberOfCompletedWorkItems++;
		averageTimeToProcessAllWorkOrders.onWorkOrderCompleted(event);
	}

}