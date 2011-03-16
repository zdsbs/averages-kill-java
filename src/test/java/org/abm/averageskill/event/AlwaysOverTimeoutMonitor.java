package org.abm.averageskill.event;

import org.abm.averageskill.TimeoutMonitor;

public class AlwaysOverTimeoutMonitor implements TimeoutMonitor {

	@Override
	public boolean timedOut() {
		return true;
	}

	@Override
	public void onTickEvent(TickEvent event) {
	}

}
