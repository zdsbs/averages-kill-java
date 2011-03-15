package org.abm.averageskill.event;

import static java.util.Arrays.asList;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import org.abm.averageskill.AveragesKill;
import org.abm.averageskill.WorkOrderCompletedListener;
import org.abm.averageskill.WorkOrderEventSource;

public class NotifiableWorkOrderCompletedEventSource implements WorkOrderEventSource, WorkOrderCompletedListener {
	private final AveragesKill simulationReport;
	private final Queue<List<WorkOrderCompletedEvent>> events = new ArrayDeque<List<WorkOrderCompletedEvent>>();
	private int currentTime = 0;
	private int timeOfLastEvent = 0;
	private final int timeout;

	public NotifiableWorkOrderCompletedEventSource(AveragesKill simulationReport, int timeout) {
		this.simulationReport = simulationReport;
		this.timeout = timeout;
	}

	@Override
	public void doWork() {
		for (WorkOrderCompletedEvent event : popNextEvents()) {
			currentTime = event.getTicks();
			if (currentTime > timeout) {
				return;
			}
			timeOfLastEvent = currentTime;
			simulationReport.onWorkOrderCompleted(event);
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
		return simulationReport.hasCompletedAllWorkOrders();
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