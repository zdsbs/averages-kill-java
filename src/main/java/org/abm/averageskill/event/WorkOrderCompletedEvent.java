package org.abm.averageskill.event;

public class WorkOrderCompletedEvent implements Event {
	private final int ticks;

	public WorkOrderCompletedEvent(int ticks) {
		this.ticks = ticks;
	}

	public static WorkOrderCompletedEvent at(int ticks) {
		return new WorkOrderCompletedEvent(ticks);
	}

	@Override
	public int getTicks() {
		return ticks;
	}
}