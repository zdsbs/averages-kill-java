package org.abm.averageskill;

import org.abm.averageskill.Lists.F;

public class WorkOrder {
	private final int totalWorkUnits;
	private int completedWork;
	private boolean allTiersHaveCompletedWorkingOnThis = false;

	public WorkOrder(int totalWorkUnits) {
		this.totalWorkUnits = totalWorkUnits;
	}

	public boolean workUnitsAreComplete() {
		return totalWorkUnits <= completedWork;
	}

	public void complete(int completedWork) {
		this.completedWork += completedWork;
	}

	public boolean haveAllTiersHaveCompletedWorkingOnThis() {
		return allTiersHaveCompletedWorkingOnThis;
	}

	public void markAllTiersHaveCompletedWorkingOnThis() {
		allTiersHaveCompletedWorkingOnThis = true;
	}

	public static F<WorkOrder, Boolean> isNotComplete() {
		return new F<WorkOrder, Boolean>() {

			@Override
			public Boolean f(WorkOrder a) {
				return !a.workUnitsAreComplete();
			}
		};
	}

}