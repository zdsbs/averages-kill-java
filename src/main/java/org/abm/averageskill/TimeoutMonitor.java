package org.abm.averageskill;

import org.abm.averageskill.event.TickEvent;

public interface TimeoutMonitor {
	public boolean timedOut();

	void onTickEvent(TickEvent event);
}
