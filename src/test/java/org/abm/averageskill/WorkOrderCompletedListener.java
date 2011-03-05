package org.abm.averageskill;

import org.abm.averageskill.event.WorkOrderCompletedEvent;

interface WorkOrderCompletedListener {
	
	void notifyOfCompletedEvent(WorkOrderCompletedEvent... events);
	
}