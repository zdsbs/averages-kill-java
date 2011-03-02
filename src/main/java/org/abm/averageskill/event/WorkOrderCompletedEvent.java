package org.abm.averageskill.event;

public class WorkOrderCompletedEvent {
	private final int ticks;

	public WorkOrderCompletedEvent(int ticks) {
		this.ticks = ticks;
	}

	public static WorkOrderCompletedEvent at(int ticks) {
		return new WorkOrderCompletedEvent(ticks);
	}

	public int getTicks() {
		return ticks;
	}
}