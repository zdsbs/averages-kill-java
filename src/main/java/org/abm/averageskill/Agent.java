package org.abm.averageskill;

import org.abm.averageskill.Lists.F;

public class Agent {
	private WorkOrder workOrder;

	public void recieve(WorkOrder workOrder) {
		this.workOrder = workOrder;
	}

	public void doSomeWork() {
		workOrder.complete(1);
	}

	public static F<Agent, Boolean> hasWork() {
		return new F<Agent, Boolean>() {
			@Override
			public Boolean f(Agent a) {
				return a.workOrder != null && !a.workOrder.workCompletedForTheTier();
			}
		};
	}

	public static F<Agent, Boolean> hasNoWork() {
		return new F<Agent, Boolean>() {
			@Override
			public Boolean f(Agent a) {
				return a.workOrder == null;
			}
		};
	}

	public static F<Agent, Boolean> haveCompletedTheirWork() {
		return new F<Agent, Boolean>() {
			@Override
			public Boolean f(Agent a) {
				if (a.workOrder != null) {
					return a.workOrder.workCompletedForTheTier();
				}
				return false;
			}
		};
	}

	public void markAllTiersHaveCompletedWorkingOnThis() {
		workOrder.markAllTiersHaveCompletedWorkingOnThis();
		workOrder = null;
	}
}