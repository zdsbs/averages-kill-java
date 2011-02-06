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
				return a.workOrder != null && !a.workOrder.isWorkCompletedForTheTier();
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
					return a.workOrder.isWorkCompletedForTheTier();
				}
				return false;
			}
		};
	}

	public static F<Agent, Boolean> finished() {
		return new F<Agent, Boolean>() {

			@Override
			public Boolean f(Agent a) {
				return a.isFinished();
			}

		};
	}

	@Override
	public String toString() {
		return "workOrder=" + workOrder;
	}

	public boolean isFinished() {
		if (workOrder == null) {
			return false;
		}
		return workOrder.isWorkCompletedForTheTier();
	}

	public WorkOrder removeWork() {
		WorkOrder workOrder = this.workOrder;
		this.workOrder = null;
		workOrder.clearWorkDone();
		return workOrder;
	}

	public void removeWorkTotallyFinish() {
		this.workOrder.markAsTotallyDone();
		this.workOrder = null;
	}

}