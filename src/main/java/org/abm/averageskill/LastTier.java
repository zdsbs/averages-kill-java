package org.abm.averageskill;

import static org.abm.averageskill.Agent.hasNoWork;
import static org.abm.averageskill.Agent.hasWork;
import static org.abm.averageskill.Lists.filter;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class LastTier extends Tier {

	public LastTier(List<Agent> initialAgents) {
		super(initialAgents);
	}

	public LastTier(List<Agent> initAgents, int tierIndex, Log log) {
		super(initAgents, tierIndex, log);
	}

	@Override
	public Deque<WorkOrder> tick() {
		List<Agent> free = filter(hasNoWork(), allAgents);
		List<Agent> working = filter(hasWork(), allAgents);
		List<Agent> finished = filter(Agent.finished(), allAgents);

		assignUnclaimedWork(free);
		doWork(working);
		Deque<WorkOrder> totallyFinish = totallyFinish(finished);
		return totallyFinish;
	}

	private Deque<WorkOrder> totallyFinish(List<Agent> finished) {
		for (Agent agent : finished) {
			agent.removeWorkTotallyFinish();
		}
		return new ArrayDeque<WorkOrder>();
	}

}
