package org.abm.averageskill;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import org.abm.averageskill.AverageTimeToProcessWorkOrdersTest.AverageTimeToProcessAllWorkOrders;
import org.abm.averageskill.event.WorkOrderCompletedEvent;
import org.junit.Test;

public class HandleWorkOrderCompletedEventsTest {

	private static final class NotifiableWorkOrderCompletedEventSource implements WorkOrderEventSource {
		private final AveragesKill simulationReport;
		private final Queue<List<WorkOrderCompletedEvent>> events = new ArrayDeque<List<WorkOrderCompletedEvent>>();

		private NotifiableWorkOrderCompletedEventSource(AveragesKill simulationReport) {
			this.simulationReport = simulationReport;
		}

		@Override
		public void doWork() {
			for (WorkOrderCompletedEvent event : getNextEvents()) {
				simulationReport.onWorkOrderCompleted(event);
			}
			simulationReport.nextEventOccursAt(getNextEventTime());
		}

		private int getNextEventTime() {
			if (events.isEmpty()) {
				return Integer.MAX_VALUE;
			}
			List<WorkOrderCompletedEvent> nextEvent = events.peek();
			if (nextEvent.isEmpty()) {
				return Integer.MAX_VALUE;
			}
			return nextEvent.get(0).getTicks();
		}

		private List<WorkOrderCompletedEvent> getNextEvents() {
			if (events.isEmpty()) {
				return new ArrayList<WorkOrderCompletedEvent>();
			}
			return events.remove();
		}

		public void notifyOfCompletedEvent(WorkOrderCompletedEvent... events) {
			List<WorkOrderCompletedEvent> asList = asList(events);
			this.events.add(asList);
		}

	}

	// We're ignoring how work is partition
	@Test
	public void with_one_work_order_that_signals_completion_early_enough() throws Exception {
		int timeout = 2;
		int expectedNumberOfWorkOrdersToComplete = 1;
		AveragesKill simulationReport = getAveragesKill(timeout, expectedNumberOfWorkOrdersToComplete);

		NotifiableWorkOrderCompletedEventSource workOrderCompletedEventSource = new NotifiableWorkOrderCompletedEventSource(simulationReport);
		workOrderCompletedEventSource.notifyOfCompletedEvent(WorkOrderCompletedEvent.at(1));
		int actualTime = simulationReport.runWithEvents(workOrderCompletedEventSource);

		assertEquals(1, actualTime);
	}

	@Test
	public void completes_when_there_are_no_more_events_even_if_the_work_has_not_been_completed() throws Exception {
		int timeout = 2;
		int expectedNumberOfWorkOrdersToComplete = 1;
		AveragesKill simulationReport = getAveragesKill(timeout, expectedNumberOfWorkOrdersToComplete);

		WorkOrderEventSource workOrderCompletedEventSource = new NotifiableWorkOrderCompletedEventSource(simulationReport);
		int actualTime = simulationReport.runWithEvents(workOrderCompletedEventSource);

		assertEquals(0, actualTime);
	}

	@Test
	public void when_the_time_of_the_next_event_is_after_the_timeout_the_simulation_terminates() throws Exception {
		int timeout = 2;
		int expectedNumberOfWorkOrdersToComplete = 2;
		final AveragesKill simulationReport = getAveragesKill(timeout, expectedNumberOfWorkOrdersToComplete);

		NotifiableWorkOrderCompletedEventSource workOrderCompletedEventSource = new NotifiableWorkOrderCompletedEventSource(simulationReport);
		workOrderCompletedEventSource.notifyOfCompletedEvent(WorkOrderCompletedEvent.at(1));
		workOrderCompletedEventSource.notifyOfCompletedEvent(WorkOrderCompletedEvent.at(10));
		int actualTime = simulationReport.runWithEvents(workOrderCompletedEventSource);

		assertEquals(1, actualTime);
	}

	@Test
	public void simulation_ends_when_the_next_event_is_after_the_current_time() throws Exception {
		int timeout = 2;
		int expectedNumberOfWorkOrdersToComplete = 2;
		final AveragesKill simulationReport = getAveragesKill(timeout, expectedNumberOfWorkOrdersToComplete);
		simulationReport.nextEventOccursAt(100);
		int actualTime = simulationReport.runWithEvents(mock(WorkOrderEventSource.class));
		assertEquals(0, actualTime);
	}

	@Test
	public void stop_working_when_the_expected_number_of_work_orders_are_completed() throws Exception {
		int timeout = 20;
		int expectedNumberOfWorkOrdersToComplete = 2;
		final AveragesKill simulationReport = getAveragesKill(timeout, expectedNumberOfWorkOrdersToComplete);

		NotifiableWorkOrderCompletedEventSource workOrderCompletedEventSource = new NotifiableWorkOrderCompletedEventSource(simulationReport);
		workOrderCompletedEventSource.notifyOfCompletedEvent(WorkOrderCompletedEvent.at(1));
		workOrderCompletedEventSource.notifyOfCompletedEvent(WorkOrderCompletedEvent.at(3));
		workOrderCompletedEventSource.notifyOfCompletedEvent(WorkOrderCompletedEvent.at(10));
		workOrderCompletedEventSource.notifyOfCompletedEvent(WorkOrderCompletedEvent.at(19));
		int actualTime = simulationReport.runWithEvents(workOrderCompletedEventSource);

		assertEquals(3, actualTime);
	}

	private AveragesKill getAveragesKill(int timeout, int expectedNumberOfWorkOrdersToComplete) {
		return new AveragesKill(timeout, expectedNumberOfWorkOrdersToComplete, new AverageTimeToProcessAllWorkOrders());
	}
	// There are things out there that can etll me a work orders are complete
	// and I need to give them a chance to tell me that work orders are
	// completed

	// THE EXERCISE:
	// finish off computing the time it takes to run
	// that should be able to be done without any work orders or agents

	// Implement average time to complete all work orders (without dealing with
	// actual work orders / agents) (this is nice because it will require a
	// little more "interesting" work to be done on the reporting side of things

	// ///////////////////
	// ZDS - Why wouldn't I just make AverageTimeToCompleteWorkOrders a workOrderCompletionListener and make sure AveragesKill sends messages to that interface
	// why go ahead with the concrete version?
	// I started (then stopped) the idea of sending event for the start of workOrders because then I'd need to associate events with specific work orders
	// and that seemed complex, but I have trouble seeing another way around that
	// How would I allow for different start times of work orders without tracking work order identity??
	// I jumped a bit ahead and made NotifiableWorkOrderCompletedEventSource it sort of made my life easier but it's complex - with lots of assumptions
	// ////////////////////////////

	// ///////////////////////////////////
	// Why do we keep tests after we've designed the code?
	// Is the main purpose of testing to help design?
	// How important is catching regressions?
	// ///////////////////////////////////
	// Pay close attentention of all the different permutations of events
	// do work() - may not fire off events - may fire off multiple events

	// These events that we're talking about so far are reporting events

	// how far can you go before you care about order of reporting events

	// There is no reason the report needs to be generated while the simulation
	// is running

	// Then what about a live running report

	// What can I ignore right now / or what's the weakest assumption I can make
	// right now.
	// Better yet when I start making an assumption ask myself can I make a
	// weaker assumption!
}
