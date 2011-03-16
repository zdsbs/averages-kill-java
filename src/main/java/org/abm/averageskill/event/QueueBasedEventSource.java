package org.abm.averageskill.event;

import static java.util.Arrays.asList;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import org.abm.averageskill.EventListener;
import org.abm.averageskill.EventSource;
import org.abm.averageskill.TimeoutMonitor;
import org.abm.averageskill.WorkOrderCompletionMonitor;

public class QueueBasedEventSource implements EventSource, EventListener {
	private final WorkOrderCompletionMonitor workOrderCompletionMonitor;
	private final Queue<List<Event>> events = new ArrayDeque<List<Event>>();
	private int timeOfLastEvent = 0;
	private final TimeoutMonitor timeoutMonitor;

	public QueueBasedEventSource(WorkOrderCompletionMonitor workOrderCompletionMonitor, TimeoutMonitor timeoutMonitor) {
		this.workOrderCompletionMonitor = workOrderCompletionMonitor;
		this.timeoutMonitor = timeoutMonitor;
	}

	// 3-15 re-writing this to be Generic Events is easy except for like 35.
	// there we only want to send this event to the simupationreport if it's a completion event.
	// what's the best way to go about this?

	@Override
	public void doWork() {

		for (Event event : popNextEvents()) {
			timeoutMonitor.onTickEvent(TickEvent.at(event.getTicks()));
			if (timeoutMonitor.timedOut()) {
				return;
			}
			timeOfLastEvent = event.getTicks();

			if (event instanceof WorkOrderCompletedEvent) {
				workOrderCompletionMonitor.onWorkOrderCompleted((WorkOrderCompletedEvent) event);
			}
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
		if (timeoutMonitor.timedOut()) {
			return true;
		}
		return workOrderCompletionMonitor.hasCompletedAllWorkOrders();
	}

	private List<Event> popNextEvents() {
		if (events.isEmpty()) {
			return new ArrayList<Event>();
		}
		return events.remove();
	}

	@Override
	public void notifyOfEvent(Event... events) {
		List<Event> asList = asList(events);
		this.events.add(asList);
	}

}