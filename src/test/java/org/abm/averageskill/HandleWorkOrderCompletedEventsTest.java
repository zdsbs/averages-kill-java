package org.abm.averageskill;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.abm.averageskill.AverageTimeToProcessWorkOrdersTest.AverageTimeToProcessAllWorkOrders;
import org.abm.averageskill.event.Event;
import org.abm.averageskill.event.QueueBasedEventSource;
import org.abm.averageskill.event.WorkOrderCompletedEvent;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

public class HandleWorkOrderCompletedEventsTest {
	public interface IRespondToTick {
		public void tick(int atTime);
	}

	public static class TimeoutEvent implements Event {
		private final int ticks;

		public TimeoutEvent(int ticks) {
			this.ticks = ticks;
		}

		public static TimeoutEvent at(int ticks) {
			return new TimeoutEvent(ticks);
		}

		@Override
		public int getTicks() {
			return ticks;
		}
	}

	public static class AllWorkCompletedEvent implements Event {
		private final int ticks;

		public AllWorkCompletedEvent(int ticks) {
			this.ticks = ticks;
		}

		@Override
		public int getTicks() {
			return ticks;
		}

		public static AllWorkCompletedEvent at(int ticks) {
			return new AllWorkCompletedEvent(ticks);
		}
	}

	public static class Simulation {
		private int haltingTime;
		private final Collection<IRespondToTick> workers;

		public Simulation(Collection<IRespondToTick> workers) {
			this.workers = workers;
		}

		public Simulation() {
			this(Collections.<IRespondToTick> emptyList());
		}

		public void handleEvent(Event event) {
			if (notAlreadyDone()) {
				haltingTime = event.getTicks();
			}
		}

		private boolean notAlreadyDone() {
			return haltingTime <= 0;
		}

		public int stoppedAt() {
			return haltingTime;
		}

		public static Simulation watching(IRespondToTick... workers) {
			return new Simulation(Arrays.<IRespondToTick> asList(workers));
		}

		public void tick(int i) {
			for (IRespondToTick each : workers) {
				each.tick(i);
			}
		}
	}

	// We're ignoring how work is partition
	@Test
	public void workCompletesEarlyEnough() throws Exception {
		Simulation simulation = new Simulation();
		simulation.handleEvent(AllWorkCompletedEvent.at(1));
		// simulate timeout monitor never raising timeout
		assertEquals(1, simulation.stoppedAt());
		// assertEquals(State.stoppedAt(1), simulation.state());
	}

	@Test
	public void workNeverCompletes() throws Exception {
		Simulation simulation = new Simulation();
		// simulate work never being completed
		simulation.handleEvent(TimeoutEvent.at(2));
		assertEquals(2, simulation.stoppedAt());
	}

	@Test
	public void workCompletesTooLate() throws Exception {
		Simulation simulation = new Simulation();
		simulation.handleEvent(TimeoutEvent.at(2));
		simulation.handleEvent(AllWorkCompletedEvent.at(3));
		assertEquals(2, simulation.stoppedAt());
	}

	@Test
	@Ignore
	public void simulationReceivesNoEvents() throws Exception {
		Simulation simulation = new Simulation();
		// nothing has happened yet to trigger stopping the simulation
		assertEquals(2, simulation.stoppedAt());
		// assertEquals(State.running(), simulation.state());
	}

	@Test @Ignore("Use Zach's idea")
	public void runUntilHaltingTime() throws Exception {
	}

	@Test
	public void forwardTickToEverythingItMonitors() throws Exception {
		IRespondToTick ticker1 = Mockito.mock(IRespondToTick.class);
		IRespondToTick ticker2 = Mockito.mock(IRespondToTick.class);
		IRespondToTick ticker3 = Mockito.mock(IRespondToTick.class);

		Simulation simulation = Simulation.watching(ticker1, ticker2, ticker3);

		simulation.tick(1);
		simulation.tick(2);
		simulation.tick(3);

		Mockito.verify(ticker1).tick(1);
		Mockito.verify(ticker1).tick(2);
		Mockito.verify(ticker1).tick(3);
		Mockito.verify(ticker2).tick(1);
		Mockito.verify(ticker2).tick(2);
		Mockito.verify(ticker2).tick(3);
		Mockito.verify(ticker3).tick(1);
		Mockito.verify(ticker3).tick(2);
		Mockito.verify(ticker3).tick(3);
	}

	/*
	 * 1) Knowing who totell to do something 2) Knowing when to stop 3)
	 * Forwarding / creating new events
	 */

	@Test
	@Ignore
	public void with_one_work_order_that_signals_completion_early_enough()
			throws Exception {

		int timeout = 2;
		int expectedNumberOfWorkOrdersToComplete = 1;

		WorkOrderCompletionMonitor workOrderCompletionMonitor = getWorkOrderCompletionMonitor(expectedNumberOfWorkOrdersToComplete);

		QueueBasedEventSource queueBasedEventSource = new QueueBasedEventSource(
				workOrderCompletionMonitor, new TickListeningTimeoutMonitor(
						timeout));

		workOrderCompletionMonitor.onWorkOrderCompleted(WorkOrderCompletedEvent
				.at(1));
		int actualTime = queueBasedEventSource.run();

		assertEquals(1, actualTime);
	}

	@Test
	public void completes_when_there_are_no_more_events_even_if_the_work_has_not_been_completed()
			throws Exception {
		int timeout = 2;
		int expectedNumberOfWorkOrdersToComplete = 1;
		WorkOrderCompletionMonitor simulationReport = getWorkOrderCompletionMonitor(expectedNumberOfWorkOrdersToComplete);

		QueueBasedEventSource workOrderCompletedEventSource = new QueueBasedEventSource(
				simulationReport, new TickListeningTimeoutMonitor(timeout));
		int actualTime = workOrderCompletedEventSource.run();

		assertEquals(0, actualTime);
	}

	@Test
	public void stop_working_when_the_expected_number_of_work_orders_are_completed()
			throws Exception {
		int timeout = 20;
		int expectedNumberOfWorkOrdersToComplete = 2;
		final WorkOrderCompletionMonitor simulationReport = getWorkOrderCompletionMonitor(expectedNumberOfWorkOrdersToComplete);

		QueueBasedEventSource workOrderCompletedEventSource = new QueueBasedEventSource(
				simulationReport, new TickListeningTimeoutMonitor(timeout));
		workOrderCompletedEventSource.notifyOfEvent(WorkOrderCompletedEvent
				.at(1));
		workOrderCompletedEventSource.notifyOfEvent(WorkOrderCompletedEvent
				.at(3));
		workOrderCompletedEventSource.notifyOfEvent(WorkOrderCompletedEvent
				.at(10));
		workOrderCompletedEventSource.notifyOfEvent(WorkOrderCompletedEvent
				.at(19));
		int actualTime = workOrderCompletedEventSource.run();

		assertEquals(3, actualTime);
	}

	private WorkOrderCompletionMonitor getWorkOrderCompletionMonitor(
			int expectedNumberOfWorkOrdersToComplete) {

		return new WorkOrderCompletionMonitor(
				expectedNumberOfWorkOrdersToComplete,
				new AverageTimeToProcessAllWorkOrders());

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
	// ZDS - Why wouldn't I just make AverageTimeToCompleteWorkOrders a
	// workOrderCompletionListener and make sure AveragesKill sends messages to
	// that interface
	// why go ahead with the concrete version?
	// I started (then stopped) the idea of sending event for the start of
	// workOrders because then I'd need to associate events with specific work
	// orders
	// and that seemed complex, but I have trouble seeing another way around
	// that
	// How would I allow for different start times of work orders without
	// tracking work order identity??
	// I jumped a bit ahead and made NotifiableWorkOrderCompletedEventSource it
	// sort of made my life easier but it's complex - with lots of assumptions
	// ////////////////////////////

	// ///////////////////////////////////
	// Why do we keep tests after we've designed the code?
	// Is the main purpose of testing to help design?
	// How important is catching regressions?
	// ///////////////////////////////////

	/*
	 * 3-2 I ignore any kinds of events after my timeout except for tick events
	 * Try and replace tests that need peeking with the tick events
	 */

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
