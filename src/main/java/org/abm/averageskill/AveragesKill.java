package org.abm.averageskill;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import org.abm.averageskill.HandleWorkOrderCompletedEventsTest.WorkOrderCompletedEvent;
import org.abm.averageskill.HandleWorkOrderCompletedEventsTest.WorkOrderCompletedEventSource;

public class AveragesKill {
	private final List<Tier> tiers = new ArrayList<Tier>();
	private final int maxNumberOfTicks;
	private final Log log;
	private int timeOfLastEvent;
	private final int expectedNumberOfWorkOrdersToComplete;
	private int nextEventAt;

	AveragesKill() {
		this.maxNumberOfTicks = Integer.MAX_VALUE;
		this.expectedNumberOfWorkOrdersToComplete = -1;
		this.log = new LogNoOp();
	}

	AveragesKill(int maxNumberOfTicks) {
		this.maxNumberOfTicks = maxNumberOfTicks;
		this.expectedNumberOfWorkOrdersToComplete = -1;
		this.log = new LogNoOp();
	}

	public AveragesKill(int timeout, int expectedNumberOfWorkOrdersToComplete) {
		this.maxNumberOfTicks = timeout;
		this.expectedNumberOfWorkOrdersToComplete = expectedNumberOfWorkOrdersToComplete;
		this.log = new LogNoOp();
	}

	public int run(WorkOrders allWorkOrders, Agents... agents) {
		int timeTook = 0;
		initializeTiers(agents);

		List<WorkOrder> workForTheTier = allWorkOrders.getAll();
		Deque<WorkOrder> unclaimedWork = new ArrayDeque<WorkOrder>(workForTheTier);
		while (!allWorkOrders.isAllWorkTotallyDone() && timeTook < maxNumberOfTicks) {
			for (Tier tier : tiers) {
				tier.addUnclaimedWork(unclaimedWork);
				unclaimedWork = tier.tick();
			}
			timeTook++;
		}
		return timeTook;
	}

	private void initializeTiers(Agents[] agents) {
		for (int i = 0; i < agents.length - 1; i++) {
			Tier tier = new Tier(agents[i].allAgents(), i, log);
			tiers.add(tier);
		}

		tiers.add(new LastTier(agents[agents.length - 1].allAgents(), agents.length, log));
	}

	public int runWithEvents(WorkOrderCompletedEventSource workOrderCompletedEventSource) {
		while (!done()) {
			workOrderCompletedEventSource.doWork();
		}
		return timeOfLastEvent;
	}

	private boolean done() {
		if (nextEventAt > maxNumberOfTicks) {
			return true;
		}
		return false;
	}

	public void onWorkOrderCompleted(WorkOrderCompletedEvent event) {
		this.timeOfLastEvent = event.getTicks();
	}

	// This is curious but it seems mightly convientent
	public void nextEventOccursAt(int nextEventAt) {
		this.nextEventAt = nextEventAt;
	}

}