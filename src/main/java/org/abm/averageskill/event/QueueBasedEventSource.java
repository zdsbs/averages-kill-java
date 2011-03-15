package org.abm.averageskill.event;

import static java.util.Arrays.asList;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import org.abm.averageskill.EventSource;
import org.abm.averageskill.WorkOrderCompletedListener;
import org.abm.averageskill.WorkOrderCompletionMonitor;

public class QueueBasedEventSource implements EventSource, WorkOrderCompletedListener {
	private final WorkOrderCompletionMonitor workOrderCompletionMonitor;
	private final Queue<List<WorkOrderCompletedEvent>> events = new ArrayDeque<List<WorkOrderCompletedEvent>>();
	private int timeOfLastEvent = 0;
	private final int timeout;

	public QueueBasedEventSource(WorkOrderCompletionMonitor simulationReport, int timeout) {
		this.workOrderCompletionMonitor = simulationReport;
		this.timeout = timeout;
	}

	// 3-15 re-writing this to be Generic Events is easy except for like 35.
	// there we only want to send this event to the simupationreport if it's a completion event.
	// what's the best way to go about this?

	@Override
	public void doWork() {

		for (WorkOrderCompletedEvent event : popNextEvents()) {
			int currentTime = event.getTicks();
			if (currentTime > timeout) {
				return;
			}
			timeOfLastEvent = currentTime;
			workOrderCompletionMonitor.onWorkOrderCompleted(event);
		}
	}

	@Override
	public boolean hasMoreEvents() {
		return !events.isEmpty();
	}

	@Override
	public int getCurrentTime() {
		return timeOfLastEvent;
	}

	@Override
	public int run() {
		while (!done()) {
			doWork();
		}
		return timeOfLastEvent;
	}

	private boolean done() {
		if (hasMoreEvents() == false) {
			return true;
		}
		return workOrderCompletionMonitor.hasCompletedAllWorkOrders();
	}

	private List<WorkOrderCompletedEvent> popNextEvents() {
		if (events.isEmpty()) {
			return new ArrayList<WorkOrderCompletedEvent>();
		}
		return events.remove();
	}

	@Override
	public void notifyOfCompletedEvent(WorkOrderCompletedEvent... events) {
		List<WorkOrderCompletedEvent> asList = asList(events);
		this.events.add(asList);
	}

}