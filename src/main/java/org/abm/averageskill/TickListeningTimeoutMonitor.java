package org.abm.averageskill;

import org.abm.averageskill.event.TickEvent;

public class TickListeningTimeoutMonitor implements TimeoutMonitor {
	private final int timeout;
	private int currentTime = 0;

	public TickListeningTimeoutMonitor(int timeout) {
		this.timeout = timeout;
	}

	@Override
	public boolean timedOut() {
		if (currentTime > timeout) {
			return true;
		}
		return false;
	}

	@Override
	public void onTickEvent(TickEvent event) {
		currentTime = event.getTicks();
	}

}
