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
	int tierIndex;
	Log log;

	public Tier(List<Agent> initialAgents, int tierIndex, Log log) {
		allAgents.addAll(initialAgents);
		this.tierIndex = tierIndex;
		this.log = log;
	}

	public Tier(List<Agent> initialAgents) {
		allAgents.addAll(initialAgents);
		this.tierIndex = 0;
	}

	public void addUnclaimedWork(Deque<WorkOrder> unclaimedWork) {
		this.unclaimedWork.addAll(unclaimedWork);
	}

	public Deque<WorkOrder> tick() {
		List<Agent> free = filter(hasNoWork(), allAgents);
		List<Agent> working = filter(hasWork(), allAgents);

		assignUnclaimedWork(free);
		doWork(working);
		Deque<WorkOrder> finishedWork = finishedWork(filter(finished(), allAgents));
		return finishedWork;
	}

	void agentsPrint() {
		if (log == null) {
			return;
		}

		String output = tabs() + "w:" + unclaimedWork.size() + "\ta:";
		for (Agent agent : allAgents) {
			if (Agent.hasWork().f(agent)) {
				output += agent.workOrder.amountComplete();
			}
		}
		log.log(output);
	}

	private String tabs() {
		String output = "";
		for (int i = 0; i < tierIndex; i++) {
			output += "\t";
		}
		return output;
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
