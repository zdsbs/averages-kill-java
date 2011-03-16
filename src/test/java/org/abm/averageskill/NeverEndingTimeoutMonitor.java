package org.abm.averageskill;

import org.abm.averageskill.event.TickEvent;

public class NeverEndingTimeoutMonitor implements TimeoutMonitor {

	@Override
	public boolean timedOut() {
		return false;
	}

	@Override
	public void onTickEvent(TickEvent event) {
	}

}
