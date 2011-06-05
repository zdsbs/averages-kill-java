package org.abm.averageskill.event;

public class AllWorkCompletedEvent {
	private final int ticks;

	public AllWorkCompletedEvent(int ticks) {
		this.ticks = ticks;
	}

	public int getTicks() {
		return ticks;
	}

	public static AllWorkCompletedEvent at(int ticks) {
		return new AllWorkCompletedEvent(ticks);
	}
}