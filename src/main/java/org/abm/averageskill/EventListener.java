package org.abm.averageskill;

import org.abm.averageskill.event.Event;

public interface EventListener {

	void notifyOfEvent(Event... events);

}