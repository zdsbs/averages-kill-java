package org.abm.averageskill;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class HandleWorkOrderCompletedEventsTest {
	public static class WorkOrderCompletedEvent {
		private final int ticks;

		public WorkOrderCompletedEvent(int ticks) {
			this.ticks = ticks;
		}

		public static WorkOrderCompletedEvent at(int ticks) {
			return new WorkOrderCompletedEvent(ticks);
		}

		public int getTicks() {
			return ticks;
		}
	}

	public interface WorkOrderCompletedEventSource {
		void doWork();
	}

	// Two kinds of events one that events that describe work being done / bob
	// did some work
	// the other is information about work being done / simulation ends / work
	// is completed / etc.

	// events that deterime when the simulation is done
	// events that describe different stats

	// We're ignoring how work is partition
	@Test
	public void with_one_work_order_that_signals_completion_early_enough() throws Exception {
		int timeout = 2;
		final AveragesKill simulationReport = new AveragesKill(timeout);

		int actualTime = simulationReport.runWithEvents(new WorkOrderCompletedEventSource() {
			@Override
			public void doWork() {
				// The WOCEvent is in both categories
				for (WorkOrderCompletedEvent event : receive()) {
					simulationReport.onWorkOrderCompleted(event);
				}
			}

			public List<WorkOrderCompletedEvent> receive() {
				return Arrays.<WorkOrderCompletedEvent> asList(WorkOrderCompletedEvent.at(1)); // stub
			}
		});

		assertEquals(1, actualTime);
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
