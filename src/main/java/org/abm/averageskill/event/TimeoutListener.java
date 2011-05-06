package org.abm.averageskill.event;

import org.abm.averageskill.HandleWorkOrderCompletedEventsTest;
import org.abm.averageskill.HandleWorkOrderCompletedEventsTest.TimeoutEvent;

public interface TimeoutListener {
	void onTimeout(TimeoutEvent at);
}