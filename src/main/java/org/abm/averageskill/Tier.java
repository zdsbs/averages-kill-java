package org.abm.averageskill;

import static org.abm.averageskill.Agent.finished;
import static org.abm.averageskill.Agent.hasNoWork;
import static org.abm.averageskill.Agent.hasWork;
import static org.abm.averageskill.Lists.filter;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Tier {
	final List<Agent> allAgents = new ArrayList<Agent>();
	private final Deque<WorkOrder> unclaimedWork = new ArrayDeque<WorkOrder>();

	public Tier(List<Agent> initialAgents) {
		allAgents.addAll(initialAgents);
	}

	public void addUnclaimedWork(Deque<WorkOrder> unclaimedWork) {
		this.unclaimedWork.addAll(unclaimedWork);
	}

	public Deque<WorkOrder> tick() {
		List<Agent> free = filter(hasNoWork(), allAgents);
		List<Agent> working = filter(hasWork(), allAgents);

		assignUnclaimedWork(free);
		doWork(working);
		return finishedWork(filter(finished(), allAgents));
	}

	Deque<WorkOrder> finishedWork(List<Agent> finished) {
		Deque<WorkOrder> completedWork = new ArrayDeque<WorkOrder>();
		for (Agent agent : finished) {
			completedWork.push(agent.removeWork());
		}
		return completedWork;
	}

	void assignUnclaimedWork(List<Agent> free) {
		for (Agent agent : free) {
			if (unclaimedWork.isEmpty() == false) {
				WorkOrder workOrder = unclaimedWork.pop();
				agent.recieve(workOrder);
			}
		}
	}

	void doWork(List<Agent> working) {
		for (Agent agent : working) {
			agent.doSomeWork();
		}
	}

}
