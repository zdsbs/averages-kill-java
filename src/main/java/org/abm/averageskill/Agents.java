package org.abm.averageskill;

import static java.util.Arrays.asList;
import static org.abm.averageskill.Agent.hasNoWork;
import static org.abm.averageskill.Agent.hasWork;
import static org.abm.averageskill.Agent.haveCompletedTheirWork;
import static org.abm.averageskill.Lists.filter;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

public class Agents {
	private final Agent[][] agentsAtTiers;

	Agents(Agent[][] agentsAtTiers) {
		this.agentsAtTiers = agentsAtTiers;
	}

	public void work() {
		for (Agent agent : agentsWithWork()) {
			agent.doSomeWork();
		}
	}

	private List<Agent> agentsWithWork() {
		return filter(hasWork(), allAgents());
	}

	private List<Agent> allAgents() {
		List<Agent> allAgents = new ArrayList<Agent>();
		for (Agent[] tier : agentsAtTiers) {
			allAgents.addAll(Arrays.asList(tier));
		}
		return allAgents;
	}

	private List<Agent> allTierOneAgents() {
		return asList(agentsAtTiers[0]);
	}

	public void passOnWork() {
	}

	public void accept(List<WorkOrder> notComplete) {
		Deque<WorkOrder> order = new ArrayDeque<WorkOrder>(notComplete);
		for (Agent withNotWork : filter(hasNoWork(), allTierOneAgents())) {
			if (order.isEmpty()) {
				return;
			}
			withNotWork.getA(order.pop());
		}
	}

	public void markCompleteWorkAsCompleted() {
		List<Agent> agentsInLastTier = agentsInLastTier();
		for (Agent agent : filter(haveCompletedTheirWork(), agentsInLastTier)) {
			agent.flagWorkAsComplete();
		}
	}

	private List<Agent> agentsInLastTier() {
		return asList(agentsAtTiers[agentsAtTiers.length - 1]);
	}
}