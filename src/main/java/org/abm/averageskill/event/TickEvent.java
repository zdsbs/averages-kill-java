package org.abm.averageskill.event;

//NOTE this should be periodic
public class TickEvent implements Event {
	private final int ticks;

	public TickEvent(int ticks) {
		this.ticks = ticks;
	}

	public static TickEvent at(int ticks) {
		return new TickEvent(ticks);
	}

	@Override
	public int getTicks() {
		return ticks;
	}

}
