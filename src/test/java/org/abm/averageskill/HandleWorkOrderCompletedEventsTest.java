package org.abm.averageskill;

import static org.junit.Assert.assertEquals;

import org.abm.averageskill.event.AllWorkCompletedEvent;
import org.abm.averageskill.event.TimeoutEvent;
import org.abm.averageskill.simulation.Simulation;
import org.junit.Ignore;
import org.junit.Test;

public class HandleWorkOrderCompletedEventsTest {
	// We're ignoring how work is partition
	@Test
	public void workCompletesEarlyEnough() throws Exception {
		Simulation simulation = new Simulation();
		simulation.onWorkCompleted(AllWorkCompletedEvent.at(1));
		// simulate timeout monitor never raising timeout
		assertEquals(1, simulation.stoppedAt());
		// assertEquals(State.stoppedAt(1), simulation.state());
	}

	@Test
	public void workNeverCompletes() throws Exception {
		Simulation simulation = new Simulation();
		// simulate work never being completed
		simulation.onTimeout(TimeoutEvent.at(2));
		assertEquals(2, simulation.stoppedAt());
	}

	@Test
	public void workCompletesTooLate() throws Exception {
		Simulation simulation = new Simulation();
		simulation.onTimeout(TimeoutEvent.at(2));
		simulation.onWorkCompleted(AllWorkCompletedEvent.at(3));
		assertEquals(2, simulation.stoppedAt());
	}

	@Test
	public void ignoresTimeoutEventComingTooLate() throws Exception {
		Simulation simulation = new Simulation();
		simulation.onWorkCompleted(AllWorkCompletedEvent.at(3));
		simulation.onTimeout(TimeoutEvent.at(5));
		assertEquals(3, simulation.stoppedAt());
	}

	@Test
	@Ignore("We don't know yet which client will care that the simulation is still running")
	public void simulationNotYetStopped() {
		// !??!?!
	}

	/*
	 * 1) Knowing who totell to do something 2) Knowing when to stop 3) Forwarding / creating new events
	 */

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
	 * 3-2 I ignore any kinds of events after my timeout except for tick events Try and replace tests that need peeking with the tick events
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
