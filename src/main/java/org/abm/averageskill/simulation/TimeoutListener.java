package org.abm.averageskill.simulation;

import org.abm.averageskill.event.TimeoutEvent;


public interface TimeoutListener {
	void onTimeout(TimeoutEvent at);
}