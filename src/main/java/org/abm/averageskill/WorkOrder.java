package org.abm.averageskill;

public class WorkOrder {
	private final int totalWorkUnits;
	private int completedWork;
	private boolean totallyDone = false;

	public WorkOrder(int totalWorkUnits) {
		this.totalWorkUnits = totalWorkUnits;
	}

	public boolean isWorkCompletedForTheTier() {
		return totalWorkUnits <= completedWork;
	}

	public boolean isTotallyDone() {
		return totallyDone;
	}

	public void markAsTotallyDone() {
		totallyDone = true;
	}

	public void complete(int completedWork) {
		this.completedWork += completedWork;
	}

	public void clearWorkDone() {
		completedWork = 0;
	}

}